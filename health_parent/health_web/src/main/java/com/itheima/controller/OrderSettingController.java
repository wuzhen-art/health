package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @ LYK
 * @ 体检预约业务
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;


    /**
     * @ LYK
     * @ 批量上传预约设置
     * @ param MultipartFile
     * @ return result
     */
    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile excelFile) throws Exception{
        //通过POI工具类读取上传表格文件,获取行数组的集合
        List<String[]> list = POIUtils.readExcel(excelFile);

        List<OrderSetting> orderSettings = new ArrayList<>();

        //遍历行数组的集合,获取每行的数组,取出数组元素,存入OrderSetting对象中,将OrderSetting保存至集合
        for (String[] array : list) {
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Date date = df.parse(array[0]);
            OrderSetting orderSetting = new OrderSetting(new Date(array[0]), Integer.parseInt(array[1]));
            orderSettings.add(orderSetting);
        }
        //根据OrderSetting的集合向数据库表格中添加或更新数据
        try {
            orderSettingService.add(orderSettings);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPLOAD_FAIL);
        }
    }


    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }


    /**
     * @ LYK
     * @ 根据日期编辑预约人数
     * @ param OrderSetting
     * @ return result
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

}
