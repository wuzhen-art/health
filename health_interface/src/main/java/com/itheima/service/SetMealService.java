package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    void addMeal(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> getSetmealList() throws IOException;


    Setmeal findById(Integer id, Integer type) throws IOException;


    Map<String, List> getSetmealReport();

}
