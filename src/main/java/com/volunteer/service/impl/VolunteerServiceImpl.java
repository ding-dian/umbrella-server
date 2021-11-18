package com.volunteer.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.common.AES;
import com.volunteer.mapper.VolunteerMapper;
import com.volunteer.service.VolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
                String password=AES.aesEncrypt(register.getPassword());
                register.setPassword(password);
                return baseMapper.insert(register);
            }
        }
        log.info("用户名或密码为空");
        return -1;

    }

    @Override
    public IPage<Volunteer> selectList(Volunteer volunteer) {
        LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
        /**
         * 查询条件 StrUtil.isNotEmpty 判断字符串是否为空，为空则不作为查询匹配条件
         *        ObjectUtil.isNotNull 同理 判断 基本类型
         */
        queryWrapper
                .eq(StrUtil.isNotEmpty(volunteer.getName()),Volunteer::getName,volunteer.getName())
                .eq(ObjectUtil.isNotNull(volunteer.getId()),Volunteer::getId,volunteer.getId())
                .eq(ObjectUtil.isNotNull(volunteer.getPhoneNumber()),Volunteer::getPhoneNumber,volunteer.getPhoneNumber())
                .eq(StrUtil.isNotEmpty(volunteer.getInstitude()),Volunteer::getInstitude,volunteer.getInstitude())
                .eq(ObjectUtil.isNotNull(volunteer.getGrade()),Volunteer::getGrade,volunteer.getGrade())
                .eq(ObjectUtil.isNotNull(volunteer.getMajor()),Volunteer::getMajor,volunteer.getMajor())
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

