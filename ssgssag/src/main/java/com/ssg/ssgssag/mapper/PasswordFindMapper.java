package com.ssg.ssgssag.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordFindMapper {
    String selectEmailById(String id);

    String updatePassword(String email, String tempPassword);
}
