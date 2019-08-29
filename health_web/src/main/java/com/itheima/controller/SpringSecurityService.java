package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.PermissionService;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @ LYK
 * @ 后台权限控制
 *
 */
@Component("springSecurityService")
public class SpringSecurityService implements UserDetailsService {
    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    /**
     * @ LYK
     * @ 认证及赋权
     * @ param string
     * @ return UserDetails
     */
    @Override //当点计登录后,此方法会处理,username就是提交过来的用户名
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据表单提交的username,查询user
        User user = userService.findUserUsername(username);

        if (user==null){
            return null;
        }

        Integer userid = user.getId();
        Set<Role> roles = roleService.findRoleByUid(userid);

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        if (roles!=null&&roles.size()>0) {
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions =permissionService.findPermissionByRid(roleId);
                if (permissions!=null&&permissions.size()>0) {
                    for (Permission permission : permissions) {
                        grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        UserDetails userDetails= new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorityList);
        return userDetails;
    }
}
