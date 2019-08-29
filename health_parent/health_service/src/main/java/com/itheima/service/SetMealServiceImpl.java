package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
//import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetMealDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void addMeal(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐,返回套餐Id
        setMealDao.addMeal(setmeal);
        Integer id = setmeal.getId();
        //遍历数组,根据套餐Id及检查组Id插入表格

        Map map = new HashMap();
        map.put("setmeal_id",id);
        for (Integer checkgroupId : checkgroupIds) {
            map.put("checkgroup_id",checkgroupId);
            setMealDao.setMealGroup(map);

        }

        //把实际存进数据库的图片,存至redis
        //jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());


    }

    @Override
    public List<Setmeal> getSetmealList() {
        List<Setmeal> list = setMealDao.getSetmealList();
        return list;
    }

    @Override
    public Setmeal findById(Integer id, Integer type) {
        Setmeal setmeal = setMealDao.findById(id);
        if (type==1){
            List<CheckGroup> checkGroups = setmeal.getCheckGroups();
            System.out.println("checkGroups = " + checkGroups.size());
        }
        System.out.println(setmeal);
        return setmeal;
    }

    @Override
    public Map<String, List> getSetmealReport() {
        Map<String,List> map = new HashMap<>();

        //将饼图中需求的setmealNames数据封装进list;
        List<String> setmealNames = new ArrayList<>();

        //将饼图中setmealCount数据封装进list中,集合中封装成map{"value":后台查询int次数,"name":"套餐名字"}
        List<Map<String,Object>> setmealCount = setMealDao.getSetmealCount();

        map.put("setmealCount",setmealCount);

        for (Map<String, Object> setmealMap : setmealCount) {
            String name = (String) setmealMap.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames);

        return map;
    }


}
