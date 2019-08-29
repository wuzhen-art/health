package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.dao.SetMealDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private SetMealDao setMealDao;

    @Override
    public Result submit(Map map) throws Exception {
        //1.根据预约日期orderDate,查询t_ordersetting表,查看当前日期是否设置可预约,若可预约,预约人数是否已满
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);

        //根据日期查询当日是否设置预约或预约人数是否已满
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByorderDate(date);
        if (orderSetting==null) {
            //当前日期还未设置可预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getNumber()==orderSetting.getReservations()) {
            //当前日期预约人数已满
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //满足当前日期可预约,预约人数未满后
        //根据手机号码查询是否是会员,若不是,直接注册成会员;若是则判断是否重复预约
        Member member = memberDao.findMemberByTelephone((String)map.get("telephone"));
        if (member==null) {
            member=new Member();
            //先注册,再添加至预约表
            member.setName((String)map.get("name"));
            member.setPhoneNumber((String)map.get("telephone"));
            member.setIdCard((String)map.get("idCard"));
            member.setSex((String)map.get("sex"));
            member.setRegTime(new Date());
            //注册会员时,返回会员id至member
            memberDao.addMember(member);
        }else {
            Integer member_id = member.getId();
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(member_id,date,null,null,setmealId);
            List<Order> list =orderDao.findOrderByOrder(order);
            if (list!=null&&list.size()!=0) {
                //表示该会员之前已经预约该日期的该体检套餐
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        //至此表示该预定体检套餐,日期可预约,预约人数未满,该用户不是会员则已经自动注册为会员/是会员,则该日期下无重复体检套餐
        //将该用户的该条体检套餐插入至t_order表中,并且在t_ordersetting表中将当日预约人数+1
        int reservations = orderSetting.getReservations()+1;
        orderSetting.setReservations(reservations);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        Order order = new Order(member.getId(),date,(String)map.get("orderType"),Order.ORDERSTATUS_NO,Integer.parseInt((String) map.get("setmealId")));

        //插入一条预约记录时,返回预约id
        orderDao.addOrder(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById(Integer id) {
        //根据id利用子查询,查询会员信息
        Member member = memberDao.findMemberById(id);
        //根据id利用子查询,查询套餐信息
        Setmeal setmeal = setMealDao.findSetMealById(id);
        //根据id查询预约信息
        Order order = orderDao.findOrderById(id);
        Map<String,Object> map=new HashMap();
        map.put("member", member.getName());
        map.put("setmeal", setmeal.getName());
        map.put("orderDate", order.getOrderDate());
        map.put("orderType", order.getOrderType());
        return map;
    }
}
















