package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.HouseStatus;
import com.atguigu.entity.*;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.pept.broker.Broker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_SHOW = "house/show";

    @Reference
    private HouseService houseService;
    @Reference
    private CommuntiyService communtiyService;
    @Reference
    private DictService dictService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;
    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters , Model model){
        PageInfo<House> page = houseService.findPage(filters);

        model.addAttribute("page",page);

        model.addAttribute("filters",filters);
        saveAllDictToModel(model);

        return PAGE_INDEX;

    }


    private void saveAllDictToModel(Model model) {
        //1. 查询所有小区列表，并且存储到请求域
        model.addAttribute("communityList", communtiyService.findAll());
        //2. 查询所有户型，并且存储到请求域
        model.addAttribute("houseTypeList", dictService.findDictListByParentDictCode("houseType"));
        //3. 查询所有楼层，并且存储到请求域
        model.addAttribute("floorList", dictService.findDictListByParentDictCode("floor"));
        //4. 查询所有的建筑结构，并且存储到请求域
        model.addAttribute("buildStructureList", dictService.findDictListByParentDictCode("buildStructure"));
        //5. 查询所有的朝向,并且存储到请求域
        model.addAttribute("directionList", dictService.findDictListByParentDictCode("direction"));
        //6. 查询所有的装修情况，并且存储到请求域
        model.addAttribute("decorationList", dictService.findDictListByParentDictCode("decoration"));
        //7. 查询所有的房屋用途，并且存储到请求域
        model.addAttribute("houseUseList", dictService.findDictListByParentDictCode("houseUse"));
    }




    @GetMapping("/create")
    public String create(Model model){
        saveAllDictToModel(model);

        return PAGE_CREATE;

    }
    @PostMapping("/save")
    public String save (House house,Model model){
        house.setStatus(HouseStatus.UNPUBLISHED.code);
        houseService.insert(house);

        return  successPage(model,"新增房源成功");


    }

    @GetMapping ("/edit/{id}")
    public String edit (@PathVariable("id") Long id, Model model) {
        House house = houseService.getById(id);

        model.addAttribute("house",house);

        saveAllDictToModel(model);

        return PAGE_EDIT;


    }

    @PostMapping("/update")
    public String update(House house ,Model model){
        houseService.update(house);


        return successPage(model,"更新房源信息成功");


    }

    @GetMapping("/delete/{id}")

    public String delete(@PathVariable("id") Long id ){
        houseService.delete(id);

        return LIST_ACTION;

    }
    @GetMapping("/publish/{id}/{status}")

    public String publish(@PathVariable("id") Long id , @PathVariable("status") int status){
        House house = new House();
        house.setId(id);
        house.setStatus(status);
        houseService.update(house);
        return LIST_ACTION;
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id,Model model){
        House house = houseService.getById(id);

        Community community = communtiyService.getById(house.getCommunityId());

        List<HouseImage> houseImage1List =  houseImageService.findHouseImageList(id,1);

        List<HouseImage> houseImage2List =  houseImageService.findHouseImageList(id,2);

        List<Broker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);

        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        model.addAttribute("house",house);
        model.addAttribute("community",community);
        model.addAttribute("houseImage1List",houseImage1List);
        model.addAttribute("houseImage2List",houseImage2List);
        model.addAttribute("houseBrokerList",houseBrokerList);
        model.addAttribute("houseUserList",houseUserList);

        return PAGE_SHOW;
    }


}
