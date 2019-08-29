package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;
import com.itheima.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;


/**
 * @ LYK
 * @ 套餐业务
 * @
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            //获取文件名
            String filename = imgFile.getOriginalFilename();
            //把文件名替换成UUID文件名
            filename = UploadUtils.getUUIDName(filename);
            //调用工具类上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), filename);
            //图片上传完成后,将图片名保存至redis
            //jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,filename);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 增加套餐项
     * @ param Setmeal,integer[]
     * @ return result
     */
    @RequestMapping("/add")
    public Result addMeal(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        System.out.println(setmeal.getImg());
        try {
            setMealService.addMeal(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }



}
