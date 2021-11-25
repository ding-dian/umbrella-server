package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.entity.Volunteer;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.VolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.util.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hefuren
 * @since 2021-11-07
 */
@Service
@Slf4j
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements VolunteerService {

    @Override
    public int register(Volunteer register) {
        //如果志愿者名字和密码不为空，就判断是否以及注册
        if (register.getName()!=null&&register.getPassword()!=null){
            //根据账号判断是否重复，deleted=0 未删除状态
            LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Volunteer::getName,register.getName())
                    .eq(Volunteer::getDeleted,0);
            Volunteer volunteer = baseMapper.selectOne(queryWrapper);
            //如果志愿者存在，返回-1 否则注册该志愿者账号
            if (ObjectUtil.isNotNull(volunteer)){
                log.info("该用户已注册");
                return -1;
            }else{
                register.setCreateAt(LocalDateTime.now());
                String password= AES.aesEncrypt(register.getPassword());
                register.setPassword(password);
                return baseMapper.insert(register);
            }
        }
        log.info("用户名或密码为空");
        return -1;

    }

    @Override
    public IPage<Volunteer> selectList(Volunteer volunteer) {
        if (ObjectUtil.isNull(volunteer)){
            volunteer = new Volunteer();
            volunteer.setPageNo(1);
            volunteer.setPageSize(20);
        }else {
            //如果传入页为空，默认第一页
            if (ObjectUtil.isNull(volunteer.getPageNo()) || volunteer.getPageNo() == 0){
                //如果页码为null或者未赋值 设置默认值1
                volunteer.setPageNo(1);
            }else if (volunteer.getPageNo() < 0){
                throw new RuntimeException("页码不能小于1");
            }
            //如果页数据为空，默认十条
            if (ObjectUtil.isNull(volunteer.getPageSize()) || volunteer.getPageSize() == 0){
                //如果页数据数为null或者未赋值 设置默认值20
                volunteer.setPageSize(20);
            }else if (volunteer.getPageSize() < 0){
                throw new RuntimeException("页数据不能小于1");
            }
        }
        LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
        /**
         * 查询条件 StrUtil.isNotEmpty 判断字符串是否为空，为空则不作为查询匹配条件
         *        ObjectUtil.isNotNull 同理 判断 基本类型
         */
        queryWrapper
                .like(StrUtil.isNotEmpty(volunteer.getName()),Volunteer::getName,volunteer.getName())
                .eq(ObjectUtil.isNotNull(volunteer.getId()),Volunteer::getId,volunteer.getId())
                .eq(ObjectUtil.isNotNull(volunteer.getPhoneNumber()),Volunteer::getPhoneNumber,volunteer.getPhoneNumber())
                .eq(StrUtil.isNotEmpty(volunteer.getInstitude()),Volunteer::getInstitude,volunteer.getInstitude())
                .like(ObjectUtil.isNotNull(volunteer.getGrade()),Volunteer::getGrade,volunteer.getGrade())
                .like(ObjectUtil.isNotNull(volunteer.getMajor()),Volunteer::getMajor,volunteer.getMajor())
                .eq(Volunteer::getDeleted,0);
        /**
         * 俩个参数 pageNo 当前页 pageSize 页大小
         */
        log.info("pageNo:【{}】，pageSize:【{}】",volunteer.getPageNo(),volunteer.getPageSize());
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
        Volunteer volunteer=baseMapper.selectById(id);
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
        if (ObjectUtil.isNotNull(volunteer)){
            return baseMapper.updateById(volunteer);
        }
            return 0;
    }

    /**
     * 删除一个志愿者
     *
     * @param id
     * @return
     * 
     */
    @Override
    public int deleteVolunteer(Integer id) {
        Volunteer volunteer = baseMapper.selectById(id);
        //根据id查询志愿者是否存在，存在才做逻辑删除 将delete设置为 1
        if (ObjectUtil.isNotNull(volunteer)){
            volunteer.setDeleted(1);
            return baseMapper.updateById(volunteer);
        }
        return 0;

    }


}

