package com.itheima.controller;


import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result sendCode(String telephone){
        try {
            //生成验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            System.out.println("code = " + code);
            //向用户手机发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
            //将验证码code保存至redis中,设置时间5min
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_ORDER+"_"+telephone, 60*5, code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            //生成并发送验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());

            System.out.println("code = " + code);

            //将验证码保存至redis,有效时长5min
            jedisPool.getResource().setex(RedisMessageConstant.SENDTYPE_LOGIN+"_"+telephone, 60*5, code.toString());
            return new Result(true,MessageConstant.UPLOAD_SUCCESS);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPLOAD_SUCCESS);
        }
    }

}
