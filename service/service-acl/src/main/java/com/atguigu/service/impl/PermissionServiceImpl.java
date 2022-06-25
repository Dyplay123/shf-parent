package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class PermissionServiceImpl  extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList =null;
        if (adminId==1L){
            permissionList= permissionMapper.findALL();
        }else{
            permissionList=  permissionMapper.findPermissionListByAdminId(adminId);

        }
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findALL() {
        List<Permission> permissionList = permissionMapper.findALL();

        return  PermissionHelper.build(permissionList);
    }

    @Override
    public List<String> findCodePermissionListByAdminId(Long adminId) {

        List<String> codePermissionList = null;
        if (adminId == 1) {
            //超级管理员:查询出所有的code
            codePermissionList = permissionMapper.findAllCodePermission();
        }else {
            //普通用户:根据adminId查询code集合
            codePermissionList = permissionMapper.findCodePermissionListByAdminId(adminId);
        }
        return codePermissionList;


    }


}
