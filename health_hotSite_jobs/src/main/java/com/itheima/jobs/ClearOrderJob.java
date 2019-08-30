package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;


import java.util.Date;


/**
 *
 * 清除当前日期之前的数据
 *
 * @author MR yang
 * @date 2019-08-29
 *
 */
public class ClearOrderJob {

    @Reference
    private OrderSettingService orderSettingService;
    /**
     *@描述   定时清理预约设置表数据
     *@参数  空
     *@返回值  空
     *@创建人  MR Yang
     *@创建时间  2019/8/29
     *@修改人和其它信息
     */

    public void clearOrder() throws Exception {
        //获得当前日期
        Date date = new Date();
        String today = DateUtils.parseDate2String(date);
        //调用清理方法
        orderSettingService.clearOrder(today);
    }

}

