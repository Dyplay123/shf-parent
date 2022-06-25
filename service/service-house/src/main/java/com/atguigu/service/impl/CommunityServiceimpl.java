package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;
import com.atguigu.mapper.CommuntiyMapper;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.CommuntiyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = CommuntiyService.class)
public class CommunityServiceimpl extends BaseServiceImpl<Community> implements CommuntiyService {
    @Autowired
    private CommuntiyMapper communtiyMapper;
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public BaseMapper<Community> getEntityMapper() {
        return communtiyMapper;
    }

    @Override
    public List<House> findAll() {
        return   communtiyMapper.findAll();

    }

    @Override
    public void delete(Long id) {
    Integer count = houseMapper.findCountByCommunityId(id);
    if (count>0){
        throw new RuntimeException("此小区下面有房源，不可删除");
    }
        super.delete(id);


    }
}
