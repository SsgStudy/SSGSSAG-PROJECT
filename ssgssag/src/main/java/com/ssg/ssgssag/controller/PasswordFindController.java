package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.service.PasswordFindService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@Controller
@Builder
public class PasswordFindController {

    private final PasswordFindService passwordFindService;


    @GetMapping("/find-password")
    @Operation(summary = "비밀번호 찾기 호출", description = "비밀번호 찾기 페이지 호출")
    public String showFindPasswordPage() {
        return "passwordfind/find-password";
    }

    @PostMapping("/find-email")
    @Operation(summary = "메일 반환", description = "입력한 아이디에 따른 메일 반환")
    public ResponseEntity<String> getEmailById(@RequestParam String id) {
        String email = passwordFindService.selectEmailById(id);
        log.info(email);
        return ResponseEntity.ok(email);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "비밀번호 리셋 호출", description = "비밀번호 리셋 페이지 호출")
    public String showResetPasswordPage() {
        return "passwordfind/reset-password";
    }

}
