package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommuntiyService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String LIST_ACTION = "redirect:/community";
    @Reference
   private CommuntiyService communtiyService;
    @Reference
    private DictService dictService;

    @RequestMapping
    public String index(@RequestParam Map<String, Object> filters, Model model) {
        if (filters.get("pageNum") == null || "".equals(filters.get("pageNum"))) {
            filters.put("pageNum", 1);
        }
        if (filters.get("pageSize") == null || "".equals(filters.get("pageSize"))) {
            filters.put("pageSize", 10);
        }

        PageInfo<Community> pageInfo = communtiyService.findPage(filters);
        if (filters.get("areaId") == null || "".equals(filters.get("areaId"))) {
            filters.put("areaId", 0);
        }
        if (filters.get("plateId") == null || "".equals(filters.get("plateId"))) {
            filters.put("plateId", 0);
        }

        model.addAttribute("page", pageInfo);
        model.addAttribute("filters", filters);

        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");

        model.addAttribute("areaList", areaList);
        return PAGE_INDEX;

    }


    private void saveAreaListToModel(Model model) {
        //4. 查询"beijing"的所有的区域列表
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        //将查询到的区域列表保存到request域中
        model.addAttribute("areaList", areaList);
    }


    @RequestMapping("/create")
    public String create(Model model){
        //调用业务层的方法查询beijing的所有的区域列表
        saveAreaListToModel(model);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Community community,Model model){
        //调用业务层的方法保存小区信息
        communtiyService.insert(community);
        return successPage(model,"保存小区信息成功");
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        Community community = communtiyService.getById(id);
        model.addAttribute("community",community);

        saveAreaListToModel(model);

        return PAGE_EDIT;
    }
    @PostMapping("/update")
    public String update(Community community , Model model){
        communtiyService.update(community);
        return successPage(model,"修改小区信息成功");

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model){
        communtiyService.delete(id);

       return LIST_ACTION;

    }




}
