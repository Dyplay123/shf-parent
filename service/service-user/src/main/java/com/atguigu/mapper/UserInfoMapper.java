package com.atguigu.mapper;

import com.atguigu.entity.UserInfo;

public interface UserInfoMapper {
    UserInfo getByPhone(String phone);

    void insert(UserInfo userInfo);

}
