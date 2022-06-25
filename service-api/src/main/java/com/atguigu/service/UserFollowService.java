package com.atguigu.service;

import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService {
    Boolean isFollow(Long userId, Long houseId);

    void addFollow(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(Long userId, Integer pageNum, Integer pageSize);

    void delete(Long id);
}
