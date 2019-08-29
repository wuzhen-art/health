package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.SetMealService;
import com.itheima.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;



/**
 * @ LYK
 * @ 统计报告业务
 *
 */
@RequestMapping("/report")
@RestController
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setMealService;

    /**
     * @ LYK
     * @ 会员统计
     * @ param
     * @ return result
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() throws Exception {

        try {
            Map<String, List> map = new HashMap<>();

            List<String> monthList = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.MONTH, -12);

            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                String month = DateUtils.parseDate2String(calendar.getTime(), "yyyy-MM");
                monthList.add(month);
            }

            map.put("months", monthList);

            List<Integer> memberCountList = memberService.findMemberCounts(monthList);

            map.put("memberCount", memberCountList);

            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 套餐统计
     * @ param
     * @ return result
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try {
            //根据前端页面需求,获取数据封装入map中响应至前端
            Map<String,List> map = setMealService.getSetmealReport();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }



}
