package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.component.RedisOperator;
import com.volunteer.controller.UmbrellaController;
import com.volunteer.entity.Umbrella;
import com.volunteer.entity.UmbrellaBorrow;
import com.volunteer.entity.Volunteer;
import com.volunteer.mapper.UmbrellaMapper;
import com.volunteer.service.UmbrellaBorrowService;
import com.volunteer.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: 梁峰源
 * @date: 2022/1/26 21:05
 * Description: 爱心雨伞数据与数据库交互实现类
 */
@Service
@Slf4j
public class UmbrellaBorrowServiceImpl extends ServiceImpl<UmbrellaMapper, UmbrellaBorrow> implements UmbrellaBorrowService {


    @Value("${umbrella.umbrellaBorrowRecordPageSize}")
    private Integer defaultPageSize;
    @Autowired
    private RedisOperator redisOperator;
    @Resource
    private VolunteerService volunteerService;

    @Override
    public IPage<UmbrellaBorrow> selectList(Volunteer volunteer) {
        if (ObjectUtil.isNull(volunteer) || ObjectUtil.isNull(volunteer.getOpenid())) {
            //没有传入查询人或者查询人没有openID，直接返回
            log.error("未传入用户信息或用户信息不正确");
            return null;
        }
        if (ObjectUtil.isNull(volunteer.getPageNo()) || volunteer.getPageNo() == 0) {
            //没有设置请求的页号默认返回第一页
            volunteer.setPageNo(1);
        }
        if (ObjectUtil.isNull(volunteer.getPageSize()) || volunteer.getPageSize() == 0) {
            //没有设置每页请求的数据，默认返回二十条数据
            volunteer.setPageSize(defaultPageSize);
        }
        QueryWrapper<UmbrellaBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openID", volunteer.getOpenid());
        log.info("pageNo:【{}】，pageSize:【{}】", volunteer.getPageNo(), volunteer.getPageSize());
        Page<UmbrellaBorrow> page = new Page<>();
        page.setCurrent(volunteer.getPageNo()).setSize(volunteer.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<UmbrellaBorrow> selectAll(Integer pageNo, Integer pageSize) {
        if (ObjectUtil.isNull(pageNo)){
            pageNo = 1;//没有输入页号默认返回第一页
        }
        if (ObjectUtil.isNull(pageSize)){
            pageSize = defaultPageSize;
        }
        IPage<UmbrellaBorrow> page = new Page<>(pageNo,pageSize);
        log.info("pageNo:【{}】，pageSize:【{}】", pageNo, pageSize);
        return baseMapper.selectPage(page,null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer borrowByVolunteer(Volunteer volunteer) {
        if(ObjectUtil.isNull(volunteer) || ObjectUtil.isNotNull(volunteer.getBorrowUmbrellaDate())){
            return -1;//返回-1表示未传入用户或该用户已经借取过雨伞了，不能借取
        }
        //先修改umbrella_borrow表
        UmbrellaBorrow umbrellaBorrow = new UmbrellaBorrow();
        umbrellaBorrow.setOpenID(volunteer.getOpenid())
                .setBorrowDate(LocalDateTime.now())
                .setBorrowStatus(1);
        int result = baseMapper.insert(umbrellaBorrow);
        //修改volunteer表
        volunteer.setBorrowUmbrellaDate(LocalDateTime.now());
        int result2 = volunteerService.update(volunteer);
        return result & result2;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer returnByVolunteer(Volunteer volunteer) {
        if(ObjectUtil.isNull(volunteer) || ObjectUtil.isNull(volunteer.getBorrowUmbrellaDate())){
            return -1;//返回-1表示未传入用户或该用户并没有借伞记录，不能还伞
        }
        //先修改umbrella_borrow表
        UpdateWrapper<UmbrellaBorrow> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("returnDate",LocalDateTime.now());
        updateWrapper.set("borrowStatus",0);
        updateWrapper.eq("borrowStatus",1);
        updateWrapper.eq("openID",volunteer.getOpenid());
        int result = baseMapper.update(null, updateWrapper);
        //修改volunteer表
        UpdateWrapper<Volunteer> updateWrapper2 = new UpdateWrapper<>();
        updateWrapper2.set("borrowUmbrellaDate",null);
        updateWrapper2.eq("openID",volunteer.getOpenid());
        boolean result2 = volunteerService.update(updateWrapper2);
        return result2 ? result : 0;
    }


    @Override
    public Integer deleteOneRecordByVolunteer(UmbrellaBorrow umbrellaBorrow) {
        return null;
    }

    @Override
    public Integer updateUmbrellaBorrowRecordByID(UmbrellaBorrow umbrellaBorrow) {
        return null;
    }

    @Override
    public Integer updateUmbrellaStatus(Umbrella umbrella) {
        return null;
    }
}
