package com.volunteer.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.AdminInfo;
import com.volunteer.entity.vo.AdminVo;
import com.volunteer.mapper.AdminInfoMapper;
import com.volunteer.service.AdminInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volunteer.util.AES;
import com.volunteer.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoyao
 * @since 2021-12-06
 */
@Service
@Slf4j
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {
    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 管理员登陆接口
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String adminLogin(String userName, String password) {
        String token = null;
        //非空校验
        if (StrUtil.isEmpty(userName)){
            throw new RuntimeException("用户名不能为空");
        }
        if (StrUtil.isEmpty(password)){
            throw new RuntimeException("密码不能为空");
        }
        //根据用户名去数据库查找
        AdminInfo adminInfo = adminInfoMapper.selectByUserName(userName);
        if (ObjectUtil.isNull(adminInfo)){
            throw new RuntimeException("用户名不存在");
        }else {
            //进行密码判断
            if (AES.aesEncrypt(password).equals(adminInfo.getPassword())){
                //adminVo value
                AdminVo adminVo=new AdminVo();
                BeanUtils.copyProperties(adminInfo,adminVo);
                //密码正确 生成一个token返回
                 token = UUID.randomUUID().toString().replace("-","");
                 String val = JSONUtil.toJsonStr(adminVo);
                //存入redis
                redisOperator.set(token,val,3600);
            }else {
                throw new RuntimeException("密码错误");
            }
        }
        return token;
    }

    /**
     * 注销接口
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        if (token==null){
            throw new RuntimeException("token为空");
        }
        redisOperator.del(token);
    }

    /**
     * 注册接口
     *
     * @param adminInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(AdminInfo adminInfo) {
        if (ObjectUtil.isNull(adminInfo)){
            throw new RuntimeException("用户数据不能为空");
        }
        if (ObjectUtil.isNull(adminInfo.getUsername())){
            throw new RuntimeException("账号不能为空");
        }
        if (ObjectUtil.isNull(adminInfo.getPassword())){
            throw new RuntimeException("密码不能为空");
        }
        if (ObjectUtil.isNull(adminInfo.getAvatar())){
            throw new RuntimeException("头像不能为空");
        }
        if (ObjectUtil.isNull(adminInfo.getName())){
            throw new RuntimeException("名字不能为空");
        }
        if (ObjectUtil.isNull(adminInfo.getRoles())){
            throw new RuntimeException("角色不能为空");
        }
        if (ObjectUtil.isNull(adminInfo.getDescription())){
            throw new RuntimeException("描述信息不能为空");
        }
        //查看是否已经注册
        AdminInfo adminIn = adminInfoMapper.selectByUserName(adminInfo.getUsername());
        if (ObjectUtil.isNotNull(adminIn)){
            throw new RuntimeException("该账号已注册");
        }
        adminInfo.setPassword(AES.aesEncrypt(adminInfo.getPassword()));
        //注册进数据库
        adminInfoMapper.insert(adminInfo);
    }


}
