package com.atguigu.base;

import com.github.pagehelper.Page;

import java.util.Map;

public interface BaseMapper<T>{

    void insert(T t);

    T getById(Long id);

    void update(T t);

    void delete(Long id);

    Page<T> findPage(Map<String, Object> filters);
}
