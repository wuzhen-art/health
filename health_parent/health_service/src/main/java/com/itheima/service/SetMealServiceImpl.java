package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.itheima.dao.SetMealDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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
    public List<Setmeal> getSetmealList() throws IOException {
        //LiHaiWen  在Redis中获得所有套餐信息json字符串   2019-08-28
        Jedis jedis = jedisPool.getResource();
        String allSetmealJsonStr = jedis.get("AllSetmeal");

        ObjectMapper objectMapper = new ObjectMapper();

        if(allSetmealJsonStr==null){
            //如果第一次查询，从数据库中查询数据存放到Redis中
            List<Setmeal> list = setMealDao.getSetmealList();
            allSetmealJsonStr = objectMapper.writeValueAsString(list);
            jedis.set("AllSetmeal",allSetmealJsonStr);
            System.err.println("第一次访问存放到Redis");
        }
        //将json数据转换为list集合
        List<Setmeal> SetmealList = objectMapper.readValue(allSetmealJsonStr,new TypeReference<List<Setmeal>>(){});
        //打印验证
        System.err.println("SetmealList = " + SetmealList);
        return SetmealList;
    }

    @Override
    public Setmeal findById(Integer id, Integer type) throws IOException {

        //LiHaiWen  在Redis中获得当前id套餐信息json字符串   2019-08-28
        Jedis jedis = jedisPool.getResource();
        String setmealJsonStr = jedis.get("Setmeal"+id);

        ObjectMapper objectMapper = new ObjectMapper();

        if(setmealJsonStr==null){
            //如果第一次查询，从数据库中查询数据存放到Redis中
            Setmeal setmeal = setMealDao.findById(id);
            setmealJsonStr = objectMapper.writeValueAsString(setmeal);
            jedis.set("Setmeal"+id,setmealJsonStr);
            System.err.println("第一次访问存放到Redis");
        }
        //将json数据转换为对象
        Setmeal setmeal = objectMapper.readValue(setmealJsonStr, new TypeReference<Setmeal>(){});
        //打印验证
        System.err.println("setmeal = " + setmeal);
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
