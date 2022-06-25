package com.atguigu.service;

import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;
import com.sun.corba.se.pept.broker.Broker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<Broker> findHouseBrokerListByHouseId(Long id);
}
