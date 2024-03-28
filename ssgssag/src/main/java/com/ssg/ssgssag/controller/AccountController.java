package com.ssg.ssgssag.controller;


import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AccountController {
    private final MemberService memberService;

    @Operation(summary = "회원 등록", description = "회원 가입 페이지 로딩")
    @GetMapping("/signup")
    public String signup(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "member/signup";
    }

    @Operation(summary = "회원 등록", description = "회원 정보 동록하기")
    @PostMapping("/signup")
    public String signup(@RequestBody MemberDTO newMember) {
        memberService.registerMember(newMember);

        return "redirect:/";
    }

    @Operation(summary = "로그인", description = "로그인 페이지 로딩")
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }

        return "member/login-form";
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @GetMapping("/logout")
    public String logout() {
        log.info("로그아웃");
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }
}
