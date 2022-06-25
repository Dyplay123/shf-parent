package com.atguigu.service;

import com.atguigu.entity.UserInfo;

public interface UserInfoService {


    UserInfo getByPhone(String phone);

    void insert(UserInfo userInfo);
}
