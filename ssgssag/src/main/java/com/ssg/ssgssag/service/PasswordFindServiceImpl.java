package com.ssg.ssgssag.service;

import com.ssg.ssgssag.mapper.PasswordFindMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Log4j2
@RequiredArgsConstructor
public class PasswordFindServiceImpl implements PasswordFindService{

    @Autowired
    private final PasswordFindMapper passwordFindMapper;

    @Override
    public String selectEmailById(String id) {
        return passwordFindMapper.selectEmailById(id);
    }

    @Override
    public String getRandomPassword() {
        int length = 8;
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        String tempPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return tempPassword.substring(0, length);
    }
}
