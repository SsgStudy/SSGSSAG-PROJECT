package com.ssg.ssgssag.service;

public interface PasswordFindService {
    String selectEmailById(String id);

    String getRandomPassword();

    String getTempPassword(String pw);

    void updateTempPassword(String id, String pw);
}
