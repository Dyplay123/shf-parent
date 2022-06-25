package com.atguigu.mapper;

import com.atguigu.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleMapper {
    List<Long> findRoleIdListByAdminId(Long id);

    void deleteByAdminId(Long adminId);

    void insertBatch(@Param("adminId") Long adminId, @Param("roleIds") List<Long> roleIds);
}
