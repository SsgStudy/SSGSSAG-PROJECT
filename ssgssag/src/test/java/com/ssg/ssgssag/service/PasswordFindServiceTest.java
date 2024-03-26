package com.ssg.ssgssag.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class PasswordFindServiceTest {

    @Autowired
    private final PasswordFindService passwordFindService;

    @Autowired
    public PasswordFindServiceTest(PasswordFindService passwordFindService) {
        this.passwordFindService = passwordFindService;
    }

    @Test
    public void selectEmailById() {
        String email = passwordFindService.selectEmailById("sojin");
        log.info(email);
    }


    @Test
    public void updateTempPassword() {
        String pw = "kasdfsd";
        passwordFindService.getTempPassword(pw);
    }
}
