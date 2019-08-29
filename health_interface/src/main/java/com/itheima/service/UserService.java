package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    Set findUserByUsername(String username);

    User findUserUsername(String username);

    void updatePassword(User user);

    void addUser(User user, Integer[] roleIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteUser(Integer id);

    User findUser(Integer id);

    List<Integer> findRoleIds(Integer id);

    void updateUser(User user, Integer[] roleIds);
}
