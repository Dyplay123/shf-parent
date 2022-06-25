package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;

import java.util.List;

public interface CommuntiyMapper extends BaseMapper<Community> {
    List<House> findAll();
}
