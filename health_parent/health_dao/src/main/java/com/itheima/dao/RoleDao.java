package com.itheima.dao;

import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface RoleDao {

    @Select("select * from t_role where id in (select role_id from t_user_role where user_id=#{userid})")
    Set<Role> findRoleById(Integer userid);
}
