package com.itheima.dao;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SetMealDao {

    void addMeal(Setmeal setmeal);

    @Insert("insert into t_setmeal_checkgroup values(#{setmeal_id},#{checkgroup_id})")
    void setMealGroup(Map map);

    @Select("select * from t_setmeal")
    List<Setmeal> getSetmealList();

    Setmeal findById(Integer id);

    @Select("select * from t_setmeal where id = (select setmeal_id from t_order where id = #{id})")
    Setmeal findSetMealById(Integer id);

    @Select("SELECT s.`name`,COUNT(o.`id`) AS value FROM t_order o,t_setmeal s WHERE o.`setmeal_id`=s.`id` GROUP BY s.`name` ")
    List<Map<String, Object>> getSetmealCount();

}
