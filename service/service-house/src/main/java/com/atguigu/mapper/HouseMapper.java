package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;

import java.util.List;

public interface HouseMapper extends BaseMapper<House> {

    Integer findCountByCommunityId(Long id);


    List<HouseVo> findListPage(HouseQueryBo houseQueryBo);

}
