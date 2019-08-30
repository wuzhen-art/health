package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;

    @RequestMapping("/getMemberSexReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = new HashMap();

            List<Map<String,Object>> memberCount = memberService.findSexCount();
            map.put("memberCount",memberCount);

            List<String> memberNames = new ArrayList();
            for (Map<String, Object> stringObjectMap : memberCount) {
                String name = (String) stringObjectMap.get("name");
                memberNames.add(name);
            }
            map.put("memberNames",memberNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("/getMemberAgeReport")
    public Result getMemberAgeReport(){
        try {
            Map<String,Object> map = new HashMap();

            List<Map<String,Object>> memberAgeCount = memberService.findAgeCount();
            map.put("memberAgeCount",memberAgeCount);

            List<String> memberAgeNames = new ArrayList();
            for (Map<String, Object> stringObjectMap : memberAgeCount) {
                String name = (String) stringObjectMap.get("name");
                memberAgeNames.add(name);
            }
            map.put("memberAgeNames",memberAgeNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }
}
