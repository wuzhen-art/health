package com.itheima.dao;

import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDao {


    @Select("select * from t_order where member_id=#{memberId} and orderDate=#{orderDate} and setmeal_id=#{setmealId}")
    List<Order> findOrderByOrder(Order order);

    void addOrder(Order order);

    @Select("select * from t_order where id=#{id}")
    Order findOrderById(Integer id);
}
