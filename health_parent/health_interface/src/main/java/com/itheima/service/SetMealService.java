package com.itheima.service;

import com.itheima.pojo.Setmeal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SetMealService {
    void addMeal(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> getSetmealList() throws JsonProcessingException, IOException;


    Setmeal findById(Integer id, Integer type) throws JsonProcessingException, IOException;


    Map<String, List> getSetmealReport();

}
