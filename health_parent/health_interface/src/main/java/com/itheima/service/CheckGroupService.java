package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findfindCheckGroupById(Integer id);

    List<Integer> findCheckItemIdsById(Integer id);

    void editCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteGroupById(Integer id);

    List<CheckGroup> findAllGroup();

}
