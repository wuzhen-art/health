package com.itheima.service;

import com.itheima.pojo.Permission;

import java.util.Set;

public interface PermissionService {
    Set<Permission> findPermissionByRid(Integer roleId);
}
