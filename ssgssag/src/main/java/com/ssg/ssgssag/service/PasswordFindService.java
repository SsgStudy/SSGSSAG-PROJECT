package com.ssg.ssgssag.service;

public interface PasswordFindService {
    String selectEmailById(String id);

//    String getRandomPassword();
//
//    String updateTempPassword(String email);

    String resetPassword(String id);
}
