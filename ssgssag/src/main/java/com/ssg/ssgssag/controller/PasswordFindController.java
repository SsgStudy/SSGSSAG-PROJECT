package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.service.PasswordFindService;
import com.ssg.ssgssag.service.UtilService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.concurrent.CompletableFuture;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@Controller
@Builder
public class PasswordFindController {

    private final PasswordFindService passwordFindService;
    private final UtilService utilService;


    @GetMapping("/find-password")
    @Operation(summary = "비밀번호 찾기 호출", description = "비밀번호 찾기 페이지 호출")
    public String showFindPasswordPage() {
        return "passwordfind/find-password";
    }

    @PostMapping("/reset-password")
    @Operation(summary = "비밀번호 리셋 호출", description = "비밀번호 리셋 페이지 호출")
    public String showResetPasswordPage(@RequestParam("inputId") String inputId) {


        String tempPassword = passwordFindService.getRandomPassword();
        String email = passwordFindService.selectEmailById(inputId);

        CompletableFuture.runAsync(()->
            utilService.sendResetPasswordLink(email, tempPassword)
        );

        String encryptPassword = passwordFindService.getTempPassword(tempPassword);
        passwordFindService.updateTempPassword(inputId, encryptPassword);
        return "passwordfind/reset-password";
    }

}
