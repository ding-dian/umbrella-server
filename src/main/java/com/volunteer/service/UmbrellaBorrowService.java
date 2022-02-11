package com.volunteer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.Umbrella;
import com.volunteer.entity.UmbrellaBorrow;
import com.volunteer.entity.Volunteer;
import io.swagger.models.auth.In;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 爱心雨伞服务类
 * </p>
 *
 * @author: 梁峰源
 * @date: 2022/1/26 18:20
 * Description: 爱心雨伞服务类
 */
public interface UmbrellaBorrowService extends IService<UmbrellaBorrow> {

    /**
     * 查询指定志愿者爱心雨伞历史使用记录
     * @param volunteer 查询的志愿者
     * @return 返回该志愿者的所有使用记录
     */
    IPage<UmbrellaBorrow> selectList(Volunteer volunteer);

    /**
     * 查询所有的借阅情况，需判断是否具有管理员访问权限
     * @param pageNo 查询的页号
     * @param pageSize 每页显示数据
     * @return 返回所有借阅信息
     */
    IPage<UmbrellaBorrow> selectAll(Integer pageNo, Integer pageSize);

    /**
     * 根据用户信息借取雨伞，添加借阅记录
     * 软件方面：借阅情况需要存入redis中
     * 硬件方面：需要硬件传回确认回复才完成一次借取操作
     * @param volunteer 用户
     * @return 1 success<br>other false
     */
    Integer borrowByVolunteer(Volunteer volunteer) throws InvocationTargetException, IllegalAccessException;

    /**
     * 根据用户归还雨伞，添加归还记录
     * 软件方面：将redis中记录删除，并将一次记录情况存入数据库，需要修改一张表(umbrella_borrow)
     * 硬件方面：需要硬件传回确认回复才完成一次归还雨伞操作
     * @param volunteer 用户
     * @return 1 success<br>other false
     */
    Integer returnByVolunteer(Volunteer volunteer);


    /**
     * 删除一条用户借阅记录，此种情况为管理员删除数据库里的记录
     * 需要修改两张表(umbrella_borrow、volunteer)
     * @param umbrellaBorrow 借阅记录
     * @return 1 success<br>other false
     */
    Integer deleteOneRecordByVolunteer(UmbrellaBorrow umbrellaBorrow);

    /**
     * 修改一条用户的借阅记录，此种情况为管理员修改数据库里的记录
     * @param umbrellaBorrow 雨伞借阅记录
     * @return 1 success<br>other false
     */
    Integer updateUmbrellaBorrowRecordByID(UmbrellaBorrow umbrellaBorrow);

    /**
     * 修改雨伞的借取/归还状态，修改表为Umbrella表
     * @param umbrella 雨伞在库记录
     * @return 1 success<br>other false
     */
    Integer updateUmbrellaStatus(Umbrella umbrella);

}
