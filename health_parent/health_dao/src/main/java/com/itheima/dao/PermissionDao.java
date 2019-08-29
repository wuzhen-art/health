package com.itheima.dao;

import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface PermissionDao {

    @Select("select * from t_permission where id in (select permission_id from t_role_permission where role_id=#{roleId})")
    Set<Permission> findPermissionByRid(Integer roleId);


}
