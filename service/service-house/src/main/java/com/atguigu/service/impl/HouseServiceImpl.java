package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.HouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public BaseMapper<House> getEntityMapper() {
        return houseMapper;
    }


    @Override
    public PageInfo<HouseVo> findListPage(Integer pageNum, Integer pageSize, HouseQueryBo houseQueryBo) {
        PageHelper.startPage(pageNum,pageSize);
       PageInfo<HouseVo> pageInfo = new PageInfo<HouseVo>(houseMapper.findListPage(houseQueryBo));
        return pageInfo;
    }
}
