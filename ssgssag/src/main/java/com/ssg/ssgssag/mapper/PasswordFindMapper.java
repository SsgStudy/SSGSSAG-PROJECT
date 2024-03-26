package com.ssg.ssgssag.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PasswordFindMapper {
    String selectEmailById(String id);

//    String updatePassword(String email, String tempPassword);

    int updatePasswordById(Map<String, Object> paramMap);
}
