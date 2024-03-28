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

    @Operation(summary = "회원 등록", description = "회원을 등록합니다.")
    @GetMapping("/signup")
    public String signup(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody MemberDTO newMember) {
        memberService.registerMember(newMember);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }

        return "member/login-form";
    }

    @GetMapping("/logout")
    public String logout() {
        log.info("로그아웃");
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }
}
