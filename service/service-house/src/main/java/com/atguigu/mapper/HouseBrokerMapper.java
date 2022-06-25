package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseBroker;
import com.sun.corba.se.pept.broker.Broker;

import java.util.List;

public interface HouseBrokerMapper extends BaseMapper<HouseBroker> {
    List<Broker> findHouseBrokerListByHouseId(Long id);
}
