package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. 根据用户到acl_admin表查找用户
        Admin admin = adminService.getByUsername(username);
        //2. 判断admin是否为null
        if (admin == null) {
            //用户名错误
            throw new UsernameNotFoundException("用户名错误");
        }
        List<String> permissionCodeList = permissionService.findCodePermissionListByAdminId(admin.getId());

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        if (permissionCodeList != null && permissionCodeList.size() > 0) {
            for (String permissionCode : permissionCodeList) {
                //每一个code就对应一个SimpleGrantedAuthority
                if (permissionCode != null) {
                    //将对象加入集合
                    grantedAuthorityList.add(new SimpleGrantedAuthority(permissionCode));
                }
            }
        }

        return new User(username,admin.getPassword(),
                grantedAuthorityList);
    }
}
