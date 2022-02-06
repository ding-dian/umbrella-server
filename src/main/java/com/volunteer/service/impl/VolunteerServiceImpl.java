package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.component.RedisOperator;
import com.volunteer.component.SecretOperator;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.VolunteerStatisticalInformation;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.mapper.VolunteerStatisticalInformationMapper;
import com.volunteer.service.VolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.util.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Service
@Slf4j
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {
    @Resource
    private VolunteerStatisticalInformationMapper volunteerStatisticalInformationMapper;

    @Autowired
    private SecretOperator secretOperator;

    @Autowired
    private RedisOperator redisOperator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(Volunteer register) {
        //如果志愿者名字和密码不为空，就判断是否已经注册
        if (register.getName() != null && register.getPassword() != null) {
            //根据账号判断是否重复，deleted=0 未删除状态
            LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Volunteer::getName, register.getName())
                    .eq(Volunteer::getDeleted, 0);
            Volunteer volunteer = baseMapper.selectOne(queryWrapper);
            //如果志愿者存在，返回-1 否则注册该志愿者账号
            if (ObjectUtil.isNotNull(volunteer)) {
                log.info("该用户已注册");
                return -1;
            } else {
                register.setCreateAt(LocalDateTime.now());
                String password = AES.aesEncrypt(register.getPassword());
                register.setPassword(password);
                register.setCreateAt(LocalDateTime.now());
                int result = baseMapper.insert(register);
                VolunteerStatisticalInformation volunteerStatisticalInformation = new VolunteerStatisticalInformation();
                volunteerStatisticalInformation.setCreateAt(LocalDateTime.now())
                        .setVolunteerId(register.getId());
                volunteerStatisticalInformationMapper.insert(volunteerStatisticalInformation);
                return result;
            }
        }
        log.info("用户名或密码为空");
        return -1;

    }

    @Override
    public IPage<Volunteer> selectList(Volunteer volunteer) {
        if (ObjectUtil.isNull(volunteer)) {
            volunteer = new Volunteer();
            volunteer.setPageNo(1)
                    .setPageSize(20);
        } else {
            //如果传入页为空，默认第一页
            if (ObjectUtil.isNull(volunteer.getPageNo()) || volunteer.getPageNo() == 0) {
                //如果页码为null或者未赋值 设置默认值1
                volunteer.setPageNo(1);
            } else if (volunteer.getPageNo() < 0) {
               log.error("页码不能小于1");
               return null;
            }
            //如果页数据为空，默认十条
            if (ObjectUtil.isNull(volunteer.getPageSize()) || volunteer.getPageSize() == 0) {
                //如果页数据数为null或者未赋值 设置默认值20
                volunteer.setPageSize(20);
            } else if (volunteer.getPageSize() < 0) {
                log.error("页数据不能小于1");
                return null;
            }
        }
        LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
        /**
         * 查询条件 StrUtil.isNotEmpty 判断字符串是否为空，为空则不作为查询匹配条件
         *        ObjectUtil.isNotNull 同理 判断 基本类型
         */
        queryWrapper
                .like(StrUtil.isNotEmpty(volunteer.getName()), Volunteer::getName, volunteer.getName())
                .eq(ObjectUtil.isNotNull(volunteer.getId()), Volunteer::getId, volunteer.getId())
                .eq(ObjectUtil.isNotNull(volunteer.getPhoneNumber()), Volunteer::getPhoneNumber, volunteer.getPhoneNumber())
                .eq(StrUtil.isNotEmpty(volunteer.getInstitude()), Volunteer::getInstitude, volunteer.getInstitude())
                .like(ObjectUtil.isNotNull(volunteer.getGrade()), Volunteer::getGrade, volunteer.getGrade())
                .like(ObjectUtil.isNotNull(volunteer.getMajor()), Volunteer::getMajor, volunteer.getMajor())
                .eq(Volunteer::getDeleted, 0);
        /**
         * 俩个参数 pageNo 当前页 pageSize 页大小
         */
        log.info("pageNo:【{}】，pageSize:【{}】", volunteer.getPageNo(), volunteer.getPageSize());
        Page<Volunteer> page = new Page<>();
        page.setCurrent(volunteer.getPageNo()).setSize(volunteer.getPageSize());

        return baseMapper.selectPage(page, queryWrapper);
    }


    /**
     * 批量删除志愿者
     *
     * @param ids
     * @return
     */
    @Override
    public void deleteList(Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            deleteVolunteer(ids[i]);
        }

    }

    /**
     * 查询单个志愿者
     *
     * @param id
     * @return
     */
    @Override
    public Volunteer selectOne(Integer id) {
        Volunteer volunteer = baseMapper.selectById(id);
        String password = AES.aesDecrypt(volunteer.getPassword());
        volunteer.setPassword(password);
        return volunteer;
    }

    /**
     * 更新志愿者信息
     *
     * @param volunteer
     * @return
     */
    @Override
    public int update(Volunteer volunteer) {
        if (ObjectUtil.isNotNull(volunteer)) {
            return baseMapper.updateById(volunteer);
        }
        return 0;
    }

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     * @param updateWrapper 修改条件
     * @return true成功<br>false失败
     */
    @Override
    public boolean update(Wrapper<Volunteer> updateWrapper) {
        if (ObjectUtil.isNotNull(updateWrapper)) {
            return baseMapper.update(null,updateWrapper)==1;
        }
        return false;
    }

    /**
     * 根据OpenId获取志愿者信息
     *
     * @param openId
     * @return
     */
    @Override
    public Volunteer getByOpenId(String openId) {
        return baseMapper.selectByOpenid(openId);
    }

    /**
     * 解析jsonObject并存入数据库
     *
     */
    @Override
    public Volunteer register(JSONObject jsonObject, String openid) {
        // 插入之前先检查是否已经存在
        Volunteer volunteer = baseMapper.selectByOpenid(jsonObject.getStr("openid"));
        if (Objects.isNull(volunteer)) {
            volunteer = new Volunteer();
            volunteer.setOpenid(openid)
                    .setNickName(jsonObject.getStr("nickName"))
                    .setGender(jsonObject.getInt("gender"))
                    .setAvatarUrl(jsonObject.getStr("avatarUrl"))
                    .setCreateAt(LocalDateTime.now())
                    .setActivityNumber(jsonObject.getInt("activity_number"))
                    .setActivityTotaltime(jsonObject.getDouble("activity_totaltime"));
            if (baseMapper.insert(volunteer) == 0) {
                log.info("志愿者新增异常");
                return null;
            }
        }
        log.info("志愿者已经存在,昵称:{}", volunteer.getNickName());
        return volunteer;
    }

    /**
     * 检查手机号是否已经被绑定
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public boolean phoneNumberIsBound(String phoneNumber) {
        String encryptStr = secretOperator.aesEncrypt(phoneNumber);
        List<Volunteer> list = baseMapper.selectList(new LambdaQueryWrapper<Volunteer>().eq(Volunteer::getPhoneNumber, encryptStr));
        log.info("size: {}", list.size());
        return !list.isEmpty();
    }

    @Override
    public Integer updateByOpenID(String openID) {
        return null;
    }

    /**
     * 删除一个志愿者
     *
     * @param id
     * @return
     */
    @Override
    public int deleteVolunteer(Integer id) {
        Volunteer volunteer = baseMapper.selectById(id);
        //根据id查询志愿者是否存在，存在才做逻辑删除 将delete设置为 1
        if (ObjectUtil.isNotNull(volunteer)) {
            volunteer.setDeleted(1);
            return baseMapper.updateById(volunteer);
        }
        return 0;

    }

    /**
     * 更新用户头像（异步方法）
     * @param token
     * @param volunteer
     * @param avatarUrl
     * @return
     */
    @Override
    public void updateAvatar(String token, Volunteer volunteer, String avatarUrl) {
        volunteer.setAvatarUrl(avatarUrl);
        if (baseMapper.updateById(volunteer) > 0) {
            // 修改成功后更新缓存
            redisOperator.set(token, JSONUtil.toJsonStr(volunteer), 7200);
        }
    }

}

