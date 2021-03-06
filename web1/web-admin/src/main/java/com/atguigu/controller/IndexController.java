package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    private static final String PAGE_INDEX = "frame/index";
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @RequestMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        String username = user.getUsername();

        Admin admin = adminService.getByUsername(username);

        Long adminId = admin.getId();

       model.addAttribute("admin",admin);

        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(adminId);

        model.addAttribute("permissionList", permissionList);
        return PAGE_INDEX;

    }
}
