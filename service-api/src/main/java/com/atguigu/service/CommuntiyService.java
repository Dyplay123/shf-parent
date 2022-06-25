package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;
import com.atguigu.entity.House;

import java.util.List;

public interface CommuntiyService extends BaseService<Community> {
    List<House> findAll();
}
