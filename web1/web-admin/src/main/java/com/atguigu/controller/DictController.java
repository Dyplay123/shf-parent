package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;


import com.atguigu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result finZnodes(@RequestParam(value = "id",defaultValue = "0") Long id ){
        List<Map<String, Object>> znodes = dictService.finZnodes(id);

        return Result.ok(znodes);

    }

    @RequestMapping("/findDictListByParentId/{parentId}")
    @ResponseBody
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId){
        //调用业务层的方法根据父节点id，查询子节点列表
        List<Dict> dictList = dictService.findDictListByParentId(parentId);
        return Result.ok(dictList);
    }
}
