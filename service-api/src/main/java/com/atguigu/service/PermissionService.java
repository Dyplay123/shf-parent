package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;

public interface PermissionService extends BaseService<Permission> {
    List<Permission> findMenuPermissionByAdminId(Long adminId);
    List<Permission> findALL();

    List<String> findCodePermissionListByAdminId(Long adminId);

}
