package com.ssg.ssgssag.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PasswordFindMapper {
    String selectEmailById(String id);

    void updatePassword(@Param("id")String id, @Param("pw")String pw);
}
