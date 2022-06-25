package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public abstract class BaseServiceImpl<T> {


   public abstract BaseMapper<T> getEntityMapper();



   public void insert(T t) {
      getEntityMapper().insert(t);
   }

   public T getById(Long id) {
      T roleById = getEntityMapper().getById(id);

      return roleById;
   }

   public void update(T t) {
      getEntityMapper().update(t);
   }


   public void delete(Long id) {
      getEntityMapper().delete(id);
   }


   public PageInfo<T> findPage(Map<String, Object> filters) {
      int pageNum = CastUtil.castInt(filters.get("pageNum"),1);
      int pageSize = CastUtil.castInt(filters.get("pageSize"),10);
      PageHelper.startPage(pageNum,pageSize);

      Page<T> roleList = getEntityMapper().findPage(filters);
      return new PageInfo<>(roleList,10);
   }
}
