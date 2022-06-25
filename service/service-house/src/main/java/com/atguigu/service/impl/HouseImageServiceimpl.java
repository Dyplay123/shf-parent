package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseImage;
import com.atguigu.mapper.HouseImageMapper;
import com.atguigu.service.HouseImageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
public class HouseImageServiceimpl extends BaseServiceImpl<HouseImage> implements HouseImageService{
    @Autowired
    private HouseImageMapper houseImageMapper;
    @Override
    public BaseMapper<HouseImage> getEntityMapper() {
        return houseImageMapper ;
    }

    @Override
    public List<HouseImage> findHouseImageList(@Param("houseId") Long houseId, @Param("type")int type) {
    return  houseImageMapper.findHouseImageList(houseId,type);

    }
}
