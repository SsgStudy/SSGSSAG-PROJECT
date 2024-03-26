package com.ssg.ssgssag.service;

import com.ssg.ssgssag.mapper.PasswordFindMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class PasswordFindServiceImpl implements PasswordFindService{

    @Autowired
    private final PasswordFindMapper passwordFindMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String selectEmailById(String id) {
        return passwordFindMapper.selectEmailById(id);
    }


//    @Override
//    public String getRandomPassword() {
//        int length = 8;
//        SecureRandom random = new SecureRandom();
//        byte[] bytes = new byte[length];
//        random.nextBytes(bytes);
//        String tempPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
//        return tempPassword.substring(0, length);
//    }
//
//    @Override
//    public String updateTempPassword(String email) {
//        // 임시 비밀번호 생성
//        String tempPassword = getRandomPassword();
//        log.info(tempPassword);
//        // 임시 비밀번호를 암호화
//        String encryptedPassword = passwordEncoder.encode(tempPassword);
//        // 데이터베이스에 암호화된 비밀번호 저장
//        return passwordFindMapper.updatePassword(email, encryptedPassword);
//    }

    @Override
    public String resetPassword(String id) {
        String tempPassword = generateRandomPassword(8); // 임시 비밀번호 생성
        String encodedPassword = passwordEncoder.encode(tempPassword); // 비밀번호 암호화

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("password", encodedPassword);

        passwordFindMapper.updatePasswordById(paramMap); // DB에 비밀번호 업데이트
        log.info(tempPassword);

        return tempPassword; // 암호화되지 않은 임시 비밀번호 반환
    }
    private String generateRandomPassword(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
