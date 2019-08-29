package com.itheima.service;

import com.itheima.pojo.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findRoleByUid(Integer userid);
}
