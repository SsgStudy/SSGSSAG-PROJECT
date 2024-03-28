package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
@Controller
@Builder
public class MemberController {
    // 로그인, 로그아웃, 본인 정보 수정, 본인 정보 조회
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "회원 등록", description = "회원을 등록합니다.")
    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody MemberDTO newMember) {
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

    @GetMapping("/info")
    public String showModifyPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        MemberVO memberVO = memberService.getOneMemberInModal(userDetails.getUsername());
        byte[] profilePic = memberVO.getbProfilePic();

        if (profilePic != null) {
            String base64Encoded = Base64.encodeBase64String(profilePic);
            model.addAttribute("bProfilePic", base64Encoded);
        } else {
            model.addAttribute("bProfilePic", null);
        }

        model.addAttribute("member", memberVO);

        return "member/member-modify";
    }

    @PatchMapping("/info")
    public ResponseEntity<String> modifyMembers(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("bProfilePic") MultipartFile bProfilePic,
            @RequestParam("vEmail") String vEmail
    ) {
        byte[] profilePic = null;

        if (!bProfilePic.isEmpty()) {
            try {
                profilePic = bProfilePic.getBytes();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("파일 처리 중 오류가 발생했습니다.");
            }
        }

        MemberDTO dto = MemberDTO.builder()
                .vMemberId(userDetails.getUsername())
                .vEmail(vEmail)
                .bProfilePic(profilePic)
                .build();

        memberService.modifyMemberInfo(dto);

        return ResponseEntity.ok("회원 정보가 성공적으로 변경되었습니다.");
    }

    @GetMapping("/profile-img")
    @ResponseBody
    public byte[] getProfileImg(@AuthenticationPrincipal UserDetails userDetails) {
        return memberService.getMemberProfileImg(userDetails.getUsername());
    }

    @PatchMapping("/password")
    @ResponseBody
    public ResponseEntity<?> modifyPassword(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody Map<String, String> param) {

        String newPw = param.get("newPw");
        String oldPw = param.get("oldPw");

        MemberDTO member = MemberDTO.builder()
                .vMemberId(userDetails.getUsername())
                .vMemberPw(passwordEncoder.encode(param.get("oldPW")))
                .vMemberNewPw(passwordEncoder.encode(param.get("newPw")))
                .build();

        if (memberService.modifyPassword(member)==1) {
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경 실패하였습니다.");

        }
    }


    @PatchMapping("/leave")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    @Operation(summary = "회원 탈퇴", description = "회원이 계정을 탈퇴합니다.")
    public String deleteAccount(@RequestParam String memberId) {

        MemberDTO memberDTO = MemberDTO.builder()
            .vMemberId(memberId).build();

        memberService.deleteMember(memberDTO);

        return "redirect:/";

    }

}
