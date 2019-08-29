package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletRequest request){
        String validateCode = (String) map.get("validateCode");
        String telephone = (String)map.get("telephone");
        String code = jedisPool.getResource().get(RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone);
        try {
            if (validateCode==null||!validateCode.equals(code)) {
                return new Result(false,MessageConstant.VALIDATECODE_ERROR);
            }
            Member member = memberService.findMemberByTelephone(telephone);
            if (member==null) {
                member=new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.registerMember(member);
            }
            //将会员信息存至session中
            request.getSession().setAttribute("member", member);

            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.LOGIN_FAIL);
        }
    }
}
