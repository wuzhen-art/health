package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    @Insert("insert into t_checkgroup_checkitem values(#{checkGroupId},#{checkItemId})")
    void setCheckGroupAndCheckItem(Map map);

    Page<CheckGroup> findByConditions(String queryString);

    @Select("select * from t_checkgroup where id =#{id}")
    CheckGroup findfindCheckGroupById(Integer id);

    @Select("select checkitem_id from t_checkgroup_checkitem where checkgroup_id=#{id}")
    List<Integer> findCheckItemIdsById(Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    @Delete("delete from t_checkgroup_checkitem where checkgroup_id=#{checkGroupId}")
    void deleteItemIdsById(Integer checkGroupId);

    @Delete("delete from t_checkgroup where id=#{id}")
    void deleteGroupById(Integer id);

    @Select("select * from t_checkgroup")
    List<CheckGroup> findAllGroup();

}
