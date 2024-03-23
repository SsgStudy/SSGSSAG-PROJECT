package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
@Controller
@Builder
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public void signUpMembers() {


    }

    @Operation(summary = "회원 등록", description = "회원을 등록합니다.")
    @PostMapping("/signup")
    public String signUpMembers(
        @RequestParam String vMemberNm,
        @RequestParam  String vMemberId,
        @RequestParam String vMemberPw,
        @RequestParam String vEmail) {

        log.info("컨트롤러 get메소드 들어옴");
        new MemberVO();
        MemberVO member = MemberVO.builder()
            .vMemberNm(vMemberNm)
            .vMemberId(vMemberId)
            .vMemberPw(vMemberPw)
            .vEmail(vEmail)
            .build();

        log.info("vo : " + member.toString());


        memberService.registerMember(member);
        return "redirect:/member/memberslist";
    }


    @GetMapping("/login")
    public void loginMembers() {

        log.info("로그인처리");

    }


    @GetMapping("/memberslist")
    @Operation(summary = "회원 목록 조회", description = "모든 회원의 목록을 조회합니다.")
    public String showAllMemberListPage(Model model) {

        model.addAttribute("memberList", memberService.getAllMembers());
        return "member/member-list";
    }


    @PostMapping("/namefilter")
    @Operation(summary = "이름을 검색하여 회원 조회", description = "검색한 이름에 해당하는 회원의 정보를 조회합니다.")
    @ResponseBody
    public ResponseEntity<List<MemberDTO>> showMemberListPageByName(@RequestBody MemberDTO member, Model model) {

        List<MemberDTO> memberList = memberService.getMemberList(member);

        model.addAttribute("memberList", memberList);
        return ResponseEntity.ok(memberList);
    }




}
