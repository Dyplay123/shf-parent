package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Override
    public BaseMapper<Admin> getEntityMapper() {
        return adminMapper;
    }

    @Override
    public void insert(Admin admin) {
        //判断用户名是否已被占用:根据username查询用户，如果能查询到数据说明用户名已被占用
        Admin usernameAdmin = adminMapper.getByUsername(admin.getUsername());
        if (usernameAdmin != null) {
            //说明用户名已被占用
            throw new RuntimeException("用户名已被占用");
        }
        //判断手机号是否已被占用
        Admin phoneAdmin = adminMapper.getByPhone(admin.getPhone());
        if (phoneAdmin != null) {
            //说明手机号已被占用
            throw new RuntimeException("手机号已被占用");
        }
        //如果用户名和手机号都没有被占用，则可以添加用户
        super.insert(admin);
    }

    @Override
    public List<Admin> findAll() {
      return  adminMapper.findAll();

    }

    @Override
    public Map<String, List<Role>> findRolesByAdmin(Long id) {
        //查询所有角色
        List<Role> allRoleList = roleMapper.findAll();
        List<Long> assignRoleIdList = adminRoleMapper.findRoleIdListByAdminId(id);
        // 创建俩List分别存储用户已分配和未分配的角色
        List<Role> unAssignRoleList = new ArrayList<>();
        List<Role> assignRoleList = new ArrayList<>();
        for (Role role : allRoleList) {
            if (assignRoleIdList.contains(role.getId())){
                assignRoleList.add(role);
            }else {
                unAssignRoleList.add(role);
            }
        }
        Map<String, List<Role>> roleMap=new HashMap<>();
        roleMap.put("unAssignRoleList",unAssignRoleList);
        roleMap.put("assignRoleList",assignRoleList);
        return roleMap;
    }

    @Override
    public void saveAssignRole(Long adminId, List<Long> roleIds) {
        //将之前所有数据删除
        adminRoleMapper.deleteByAdminId(adminId);
        //2. 将要分配的数据新增
        adminRoleMapper.insertBatch(adminId,roleIds);
    }

    @Override
    public Admin getByUsername(String username) {
        Admin admin = adminMapper.getByUsername(username);
        return admin;
    }
}
