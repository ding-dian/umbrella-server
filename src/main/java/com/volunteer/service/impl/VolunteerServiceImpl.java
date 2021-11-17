package com.volunteer.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.volunteer.entity.Volunteer;
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

        if (register.getName()!=null&&register.getPassword()!=null){
            LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Volunteer::getName,register.getName())
                    .eq(Volunteer::getPassword,register.getPassword())
                    .eq(Volunteer::getDeleted,0);
            System.out.println("se=sss");
            Volunteer volunteer = baseMapper.selectOne(queryWrapper);
            if (ObjectUtil.isNotNull(volunteer)){
                log.info("该用户已注册");
                return -1;
            }else{
                register.setCreateAt(LocalDateTime.now());
                return baseMapper.insert(register);
            }
        }
        log.info("用户名或密码为空");
        return -1;

    }

    @Override
    public IPage<Volunteer> selectList(Volunteer volunteer) {
        LambdaQueryWrapper<Volunteer> queryWrapper = new LambdaQueryWrapper<>();
        // 查询条件
        queryWrapper
                .eq(StrUtil.isNotEmpty(volunteer.getName()),Volunteer::getName,volunteer.getName())
                .eq(ObjectUtil.isNotNull(volunteer.getId()),Volunteer::getId,volunteer.getId())
                .eq(ObjectUtil.isNotNull(volunteer.getPhoneNumber()),Volunteer::getPhoneNumber,volunteer.getPhoneNumber())
                .eq(StrUtil.isNotEmpty(volunteer.getInstitude()),Volunteer::getInstitude,volunteer.getInstitude())
                .eq(ObjectUtil.isNotNull(volunteer.getGrade()),Volunteer::getGrade,volunteer.getGrade())
                .eq(ObjectUtil.isNotNull(volunteer.getMajor()),Volunteer::getMajor,volunteer.getMajor())
                .eq(Volunteer::getDeleted,0);
        // 分页
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
     * 删除一个志愿者
     *
     * @param id
     * @return
     * 
     */
    @Override
    public int deleteVolunteer(Integer id) {
        Volunteer volunteer = baseMapper.selectById(id);
        if (ObjectUtil.isNotNull(volunteer)){
            volunteer.setDeleted(1);
            return baseMapper.updateById(volunteer);
        }
        return 0;

    }


}

