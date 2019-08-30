package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        if (list!=null&&list.size()>0) {
            for (OrderSetting orderSetting : list) {
                //检查此orderSetting的日期在数据库中是否已存在
                int i = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());

                System.out.println(orderSetting.getOrderDate());

                if (i>0) {
                    //表明数据库已存在该日期的orderSetting,则更新number
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    //表名数据库无该日期的orderSetting,则添加至数据库
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String beginDate = date+"-01";
        String endDate = date+"-31";
        Map params= new HashMap();
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(params);

        System.out.println("list = " + list);

        //用以封装OrderSetting对象的map集合
        List<Map> listMap = new ArrayList<>();

        if (list!=null) {
            for (OrderSetting orderSetting : list) {
                Map map = new HashMap();
                //将数据库查询出的OrderSetting对象按照页面数据展示要求的格式封装进map返回
                map.put("date", orderSetting.getOrderDate().getDate());
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                listMap.add(map);
            }
        }
        return listMap;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {

        int i = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());

        if (i>0) {
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }


    }

    /**
     *@描述 清理预约设置表的数据
     *@参数   today(当前日期)
     *@返回值  空
     *@创建人  MR Yang
     *@创建时间  2019/8/29
     *@修改人和其它信息
     */
    @Override
    public void clearOrder(String today) {
        orderSettingDao.clearOrder(today);
    }



}
