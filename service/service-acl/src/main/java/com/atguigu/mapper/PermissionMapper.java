package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findALL();

    List<Permission> findPermissionListByAdminId(Long adminId);

    List<String> findCodePermissionListByAdminId(Long adminId);

    List<String> findAllCodePermission();
}
