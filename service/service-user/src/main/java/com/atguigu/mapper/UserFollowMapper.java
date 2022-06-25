package com.atguigu.mapper;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper {
    UserFollow findByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    void insert(UserFollow userFollow);

    void update(UserFollow userFollow);

    Page <UserFollowVo> findListPage(Long userId);

    void delete(Long id);
}
