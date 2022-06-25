package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {

    List<Map<String,Object>> finZnodes(Long id);

    List<Dict> findDictListByParentDictCode(String parentId);

    List<Dict> findDictListByParentId(Long parentId);
}
