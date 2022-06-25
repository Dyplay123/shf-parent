package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    void saveRolePermission(Long roleId, List<Long> permissionIds);


}
