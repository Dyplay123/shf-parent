package com.atguigu;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.pept.broker.Broker;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;
    @Reference
    private CommuntiyService communtiyService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;
    @Reference
    private UserFollowService userFollowService;

    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize,
                               @RequestBody HouseQueryBo houseQueryBo) {
        //1. 调用业务层的方法搜索房源的分页数据
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryBo);
        //2. 返回结果
        return Result.ok(pageInfo);
    }
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session){
        Map<String,Object> responseMap = new HashMap<>();
        //当前房源信息
        House house = houseService.getById(id);
        responseMap.put("house",house);
        //小区信息,得知道小区id
        Community community = communtiyService.getById(house.getCommunityId());
        responseMap.put("community",community);
        //房源的经纪人列表
        List<Broker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);
        responseMap.put("houseBrokerList",houseBrokerList);
        //房源的房源图片列表
        List<HouseImage> houseImage1List = houseImageService.findHouseImageList(id, 1);
        responseMap.put("houseImage1List",houseImage1List);
        //房源的房东列表
        List<HouseUser> houseUserList = houseUserService.findHouseUserListByHouseId(id);
        responseMap.put("houseUserList",houseUserList);
        //mei未做登录，默认设置为false

        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        if (userInfo==null){
        responseMap.put("isFollow",false);

        }else {
            responseMap.put("isFollow",userFollowService.isFollow(userInfo.getId(),id));
        }

        //返回结果
        return Result.ok(responseMap);
    }
}
