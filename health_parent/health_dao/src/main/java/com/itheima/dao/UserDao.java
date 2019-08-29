package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("select * from t_user where username=#{username}")
    User findUserByUsername(String username);
}
