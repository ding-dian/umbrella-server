package com.volunteer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.Umbrella;
import com.volunteer.entity.UmbrellaHistoryBorrow;
import com.volunteer.entity.UmbrellaOrder;
import com.volunteer.entity.Volunteer;
import com.volunteer.entity.vo.UmbrellaHistoryVo;
import com.volunteer.entity.vo.UmbrellaOrderVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 爱心雨伞服务类
 * </p>
 *
 * @author: 梁峰源
 * @date: 2022/1/26 18:20
 * Description: 爱心雨伞服务类
 */
public interface UmbrellaBorrowService extends IService<UmbrellaHistoryBorrow> {

    /**
     * 查询指定志愿者爱心雨伞历史使用记录
     * @param volunteer 查询的志愿者
     * @return 返回该志愿者的所有使用记录
     */
    List<UmbrellaHistoryVo> selectHistoryById(Volunteer volunteer);

    /**
     * 查询所有用户的所有借阅情况，需判断是否具有管理员访问权限
     * @param pageNo 查询的页号
     * @param pageSize 每页显示数据
     * @return 返回所有借阅信息
     */
    Map<String,Object> selectHistoryAll(Integer pageNo, Integer pageSize);

    /**
     * 从redis中查询所有实时借阅雨伞的信息
     * @param pageNo 查询的页号
     * @param pageSize 每页显示数据
     * @return 返回所有借阅信息
     */
     Map<String,Object> selectBorrow(Integer pageNo, Integer pageSize) throws InvocationTargetException, IllegalAccessException;

    /**
     * 从redis中查询所有超时的用户信息
     * @param pageNo 查询的页号
     * @param pageSize 每页显示数据
     * @return 返回所有借阅信息
     */
    Map<String,Object> selectOvertime(Integer pageNo, Integer pageSize);

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
     * @param umbrellaHistoryBorrow 借阅记录
     * @return 1 success<br>other false
     */
    Integer deleteOneRecordByVolunteer(UmbrellaHistoryBorrow umbrellaHistoryBorrow);

    /**
     * 修改一条用户的借阅记录，此种情况为管理员修改数据库里的记录
     * @param umbrellaHistoryBorrow 雨伞借阅记录
     * @return 1 success<br>other false
     */
    Integer updateUmbrellaBorrowRecordByID(UmbrellaHistoryBorrow umbrellaHistoryBorrow);

    /**
     * 修改雨伞的借取/归还状态，修改表为Umbrella表
     * @param umbrella 雨伞在库记录
     * @return 1 success<br>other false
     */
    Integer updateUmbrellaStatus(Umbrella umbrella);

    /**
     * 更新redis中爱心雨伞的借取时间
     */
    void updateBorrowDurationJob();

    /**
     * 借取雨伞用户违规超时策略
     * @param map key为用户存在redis中的key，value为用户借取雨伞的时间
     */
    void borrowOvertimeHandle(Map<String,Double> map);
}
