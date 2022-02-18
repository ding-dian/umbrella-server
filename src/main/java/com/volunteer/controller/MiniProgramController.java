package com.volunteer.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.volunteer.component.OSSOperator;
import com.volunteer.component.RedisOperator;
import com.volunteer.entity.common.Result;
import com.volunteer.entity.common.ResultGenerator;
import com.volunteer.entity.vo.MiniProgramSwiperListVo;
import com.volunteer.entity.vo.MiniProgramSwiperVo;
import com.volunteer.util.BeanMapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 梁峰源
 * @date: 2022/2/15 14:53
 * Description: 微信小程序资源管理模块
 */
@Api(tags = "微信小程序资源管理模块" )
@Slf4j
@RestController
@RequestMapping("/miniProgram")
public class MiniProgramController {
    @Autowired
    private OSSOperator ossOperator;
    @Autowired
    private RedisOperator redisOperator;

    /**
     * 上传微信小程序轮播图和活动介绍图片
     * @param file 图片
     * @return 返回可以公网访问到的url
     */
    @PostMapping("upLoadImage")
    @ApiOperation("上传图片接口")
    public Result upLoadImage(MiniProgramSwiperVo swiperVo,@RequestPart("file") MultipartFile file){
        if(ObjectUtil.isNull(swiperVo) || StringUtils.isEmpty(swiperVo.getStorePath()) || ObjectUtil.isNull(file)){
            return ResultGenerator.getFailResult("未选择图片模块");
        }
        String url = null;
        try {
            //根据storePath上传图片
            url = ossOperator.uploadObjectOSS(swiperVo.getStorePath(), file);
            log.info("图片上传成功:{}",url);
            //将图片信息封装起来
            swiperVo.setUrl(url);
            //存到redis中，用storePath+英文冒号+图片url组成
            String key = swiperVo.getStorePath() + ":" +url;
            String value = JSONUtil.toJsonStr(swiperVo);
            redisOperator.set(key,value);
        }catch (Exception e){
            return ResultGenerator.getFailResult("图片上传失败,"+e.getMessage());
        }
        return ResultGenerator.getSuccessResult(url);
    }

    /**
     * 根据在微信小程序中的路径获得服务器中存储的图片
     * @param storePath ex：qxImages/categoryImages0/
     * @return MiniProgramSwiperListVo
     */
    @GetMapping("getImageList")
    @ApiOperation("获取图片接口")
    public Result getImageList(String storePath){
        if(ObjectUtil.isNull(storePath) || StringUtils.isEmpty(storePath)){
            return ResultGenerator.getFailResult("没有选择小程序轮播图模块");
        }
        //从redis中拿到图片集合
        Set<String> keys = redisOperator.keys(storePath + ":*");
        List<MiniProgramSwiperVo> collect = keys.stream()
                .map(e -> JSONUtil.toBean(redisOperator.get(e), MiniProgramSwiperVo.class))
                .collect(Collectors.toList());
        //返回该对象
        MiniProgramSwiperListVo listVo = new MiniProgramSwiperListVo();
        listVo.setSwiperNum(keys.size()).setSwiperList(collect);
        String jsonStr = JSONUtil.toJsonStr(listVo);
        return ResultGenerator.getSuccessResult(jsonStr);
    }

    /**
     * 前端删除轮播图里面的图片，注意，并没有真正删除oss中存储的数据，只是让外界访问不到。作者觉得这些图片应该还是蛮珍贵的，不删！
     * @param key 图片对应在redis中的key，由storePath+英文冒号+图片url组成
     * @return result
     */
    @GetMapping("deleteImage")
    @ApiOperation("删除图片接口")
    public Result deleteImage(String key){
        if(ObjectUtil.isNull(key) || StringUtils.isEmpty(key)){
            return ResultGenerator.getFailResult("服务器没有收到图片保存在redis的key，删除失败");
        }
        redisOperator.del(key);
        return ResultGenerator.getSuccessResult();
    }

    /**
     * 这个接口主要是用户对和图片url一起包装的其他参数的修改，例如summary
     * @param swiperVo 包装好的图片对象
     * @return result
     */
    @GetMapping("updateImage")
    @ApiOperation("更新图片信息接口")
    public Result updateImage(MiniProgramSwiperVo swiperVo){
        if(ObjectUtil.isNull(swiperVo)){
            return ResultGenerator.getFailResult("服务器没有收到需要修改的图片信息，删除失败");
        }
        String key = swiperVo.getStorePath()+":"+swiperVo.getUrl();
        try {
            redisOperator.set(key,JSONUtil.toJsonStr(swiperVo));
        }catch (Exception e){
            return ResultGenerator.getFailResult("服务器异常，删除失败"+e.getMessage());
        }
        return ResultGenerator.getSuccessResult("更新成功");
    }
}
