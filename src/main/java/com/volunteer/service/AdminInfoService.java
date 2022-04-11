package com.volunteer.service;

import com.volunteer.entity.AdminInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.volunteer.entity.vo.AdminVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-06
 */
public interface AdminInfoService extends IService<AdminInfo> {
    /**
     * 管理员登陆接口
     * @param userName
     * @param password
     * @return
     */
    String adminLogin(String userName,String password);

    /**
     * 注销接口
     * @param token
     */
     void logout(String token);

    /**
     * 注册接口
     * @param adminInfo
     * @return
     */
     void register(AdminInfo adminInfo);
}
