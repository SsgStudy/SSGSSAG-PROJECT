package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
@Controller
@Builder
public class MemberController {
    // 로그인, 로그아웃, 본인 정보 수정, 본인 정보 조회
    private final MemberService memberService;
    @Operation(summary = "회원 등록", description = "회원을 등록합니다.")
    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody MemberDTO newMember) {
        log.info("member signup {}", newMember);
        memberService.registerMember(newMember);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login-form";
    }

    @GetMapping("/logout")
    public String logout() { return "redirect:/";}

    @GetMapping("check/id")
    @Operation(summary = "회원 중복 확인", description = "회원가입 시 중복된 아이디인지 검사합니다.")
    public ResponseEntity<Boolean> checkUserDuplicate(@RequestParam String vMemberId) {
        boolean check = memberService.checkIdInfo(vMemberId);
        return ResponseEntity.ok(check);
    }



}
