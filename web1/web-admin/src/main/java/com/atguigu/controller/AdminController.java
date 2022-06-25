package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Role;
import com.atguigu.service.AdminService;
import com.atguigu.util.FileUtil;
import com.atguigu.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final String PAGE_UPLOAD = "admin/upload";
    private static final String PAGE_ASSIGNSHOW ="admin/assignShow" ;
    @Reference
  private AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PAGE_INDEX ="admin/index";
    private static final String PAGE_EDIT ="admin/edit";


    @RequestMapping
    @Transactional(propagation = Propagation.SUPPORTS)
    public String index(@RequestParam Map<String,Object> filters, Model model){
      if (filters.get("pageNum") == null || "".equals(filters.get("pageNum"))){
          filters.put("pageNum",1);
      }
        if (filters.get("pageSize") == null || "".equals(filters.get("pageSize"))){
            filters.put("pageSize",10);
        }
        //调用业务层处理业务
        PageInfo<Admin> pageInfo = adminService.findPage(filters);

        model.addAttribute("page",pageInfo);
        model.addAttribute("filters",filters);
        return PAGE_INDEX;


    }

    @RequestMapping("/save")
  public String insert (Admin admin , Model model){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
      adminService.insert(admin);
      return successPage(model,"添加新用户成功");
    }

    @RequestMapping("/edit/{id}")
    public String insert (@PathVariable Long id ,Model model){
        Admin byId = adminService.getById(id);

        model.addAttribute(byId);

        return PAGE_EDIT;


    }
    @PostMapping ("/update")
    public String update (Admin admin , Model model){
        adminService.update(admin);

        return successPage(model,"修改用户成功");


    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id ){
        adminService.delete(id);

        return "redirect:/admin";

    }

    @GetMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable("id") Long id,Model model){
        model.addAttribute("id",id);
        return PAGE_UPLOAD;

    }
    @PostMapping("/upload/{id}")
    public String upload(@RequestParam("file")MultipartFile file,
                         @PathVariable("id") Long id,
                         Model model) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uuidName = FileUtil.getUUIDName(originalFilename);

        QiniuUtils.upload2Qiniu(file.getBytes(),uuidName);

        String url = QiniuUtils.getUrl(uuidName);

        Admin admin = new Admin();
        admin.setId(id);
        admin.setHeadUrl(url);

        adminService.update(admin);


        return successPage(model,"上传头像成功");




    }
    @GetMapping("/assignShow/{id}")
    public String assignShow(@PathVariable("id") Long id , Model model){

    Map<String, List<Role>> roles = adminService.findRolesByAdmin(id);

        //将查询到的已分配的角色列表和未分配的角色列表存入请求域
        model.addAllAttributes(roles);
        //将用户id存入请求域
        model.addAttribute("adminId",id);
        return PAGE_ASSIGNSHOW;
    }
   @PostMapping("/assignRole")
  public String assignRole(@RequestParam("adminId") Long adminId,
                           @RequestParam("roleIds") List<Long> roleIds,
                           Model model){
    adminService.saveAssignRole(adminId,roleIds);

    return successPage(model,"分配角色成功");

  }




}
