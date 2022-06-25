package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {

    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/";
    @Reference
    private AdminService adminService;
   @Reference
   private HouseBrokerService houseBrokerService;
    @GetMapping("/create")
    public String create(HouseBroker houseBroker, Model model){
        model.addAttribute("houseBroker",houseBroker);

        saveAdminListToModel(model);
        return PAGE_CREATE;

    }

    private void saveAdminListToModel(Model model) {
        //查询出admin列表
        List<Admin> adminList = adminService.findAll();
        //将admin列表存储到请求域
        model.addAttribute("adminList",adminList);
    }

    @PostMapping("/save")
    public String save(HouseBroker houseBroker,Model model){
        Admin admin = adminService.getById(houseBroker.getBrokerId());

        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerId(admin.getId());

        houseBrokerService.insert(houseBroker);

        return successPage(model,"新增经纪人信息成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        HouseBroker houseBroker = houseBrokerService.getById(id);

        model.addAttribute("houseBroker",houseBroker);
        saveAdminListToModel(model);

        return PAGE_EDIT;

    }

    @PostMapping("/update")
    public String update(HouseBroker houseBroker,Model model){
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBrokerService.update(houseBroker);

        return successPage(model,"修改经纪人信息成功");

    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id){
        houseBrokerService.delete(id);

        return SHOW_ACTION + houseId;

    }





}
