package com.itheima.dao;


import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> selectConditions(String queryString);

    @Select("select count(*) from t_checkgroup_checkitem where checkitem_id = #{a}")
    int findCheckGroupById(Integer checkitem_id);

    @Delete("delete from t_checkitem where id=#{id}")
    void deleteItemById(Integer checkitem_id);

    void editItem(CheckItem checkItem);

    @Select("select * from t_checkitem")
    List<CheckItem> findAllItems();

}
