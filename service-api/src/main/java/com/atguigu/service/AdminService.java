package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Map<String, List<Role>> findRolesByAdmin(Long id);

    void saveAssignRole(Long adminId, List<Long> roleIds);

    Admin getByUsername(String username);
}
