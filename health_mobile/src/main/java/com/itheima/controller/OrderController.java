package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        //获取前端传递的验证码
        String validateCode = (String) map.get("validateCode");
        //获取jedis内此手机存入的验证码
        String telephone = (String) map.get("telephone");
        String code = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone);
        //如果验证码超时或错误则响应回前端数据
        if (code==null||!code.equals(validateCode)) {
            //验证码超时或错误
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        try {
            //明确预约方式:
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            //调用业务层方法
            Result result = orderService.submit(map);
            if (result.isFlag()) {
                String orderDate = (String)map.get("orderDate");
                try {
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,"服务器异常,请稍后尝试");
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.UPLOAD_SUCCESS,map);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPLOAD_SUCCESS);
        }
    }

}












