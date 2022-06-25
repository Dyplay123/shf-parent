package com.atguigu.mapper;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictMapper {



    List<Dict> findListByParentId(Long parentId);

    Integer countIsParent(Long parentId);

    List<Dict> findDictListByParentDictCode(String parentId);

    List<Dict> findDictListByParentId(Long parentId);
}
