package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {

    @Select("select count(*) from t_ordersetting where orderDate = #{orderDate}")
    int findCountByOrderDate(Date orderDate);

    @Update("update t_ordersetting set number=#{number} where orderdate=#{orderDate}")
    void editNumberByOrderDate(OrderSetting orderSetting);

    @Insert("insert into t_ordersetting (orderDate,number) values (#{orderDate},#{number})")
    void add(OrderSetting orderSetting);

    @Select("select * from t_ordersetting where orderDate between #{beginDate} and #{endDate}")
    List<OrderSetting> getOrderSettingByMonth(Map date);

    @Select("select*from t_ordersetting where orderdate=#{orderDate}")
    OrderSetting findOrderSettingByorderDate(Date orderDate);

    @Insert("update t_ordersetting set reservations = #{reservations} where orderDate=#{orderDate}")
    void editReservationsByOrderDate(OrderSetting orderSetting);
    @Delete("DELETE from t_ordersetting where orderDate <=#{today}")
    void clearOrder(String today);
}
