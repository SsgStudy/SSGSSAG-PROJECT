package com.ssg.ssgssag.controller;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

	// 모든 회원 조회, 모든 회원 정보 수정
	private final MemberService memberService;

	@PreAuthorize("hasAuthority(MemberRole.ADMIN.getValue())")
	@GetMapping("/list")
	@Operation(summary = "회원 목록 조회", description = "모든 회원의 목록을 조회합니다.")
	public String showAllMemberListPage(Model model) {
		log.info("AdminController list");
		model.addAttribute("memberList", memberService.getAllMembers());
		return "admin/member-list";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/namefilter")
	@Operation(summary = "이름을 검색하여 회원 조회", description = "검색한 이름에 해당하는 회원의 정보를 조회합니다.")
	@ResponseBody
	public ResponseEntity<List<MemberDTO>> showMemberListPageByName(@RequestBody MemberDTO member, Model model) {
		log.info("member search {}", member);
		List<MemberDTO> memberList = memberService.getMemberList(member);

		model.addAttribute("memberList", memberList);
		return ResponseEntity.ok(memberList);
	}

	//모달창에 개인 회원 정보 출력
    @PreAuthorize("isAuthenticated()")
	@GetMapping("/get-one-member")
	@ResponseBody
	public MemberVO getMembersInModal(@RequestParam("memberId") String memberId) {
		log.info("member id = {}", memberId);

		return memberService.getOneMemberInModal(memberId);
	}


	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify-member-info")
	@Operation(summary = "회원 정보 수정", description = "총관리자가 회원들의 정보를 수정합니다.")
	public String modifyMembers(
		@RequestParam String memberId,
		@RequestParam String memberName,
		@RequestParam String memberPw,
		@RequestParam String memberEmail,
		@RequestParam String memberAuth
	) {

		log.info("emails {}", memberEmail);

		MemberDTO dto = MemberDTO.builder()
			.vMemberId(memberId)
			.vMemberNm(memberName)
			.vMemberPw(memberPw)
			.vEmail(memberEmail)
			.vMemberAuth(memberAuth).build();


		log.info("회원 아이디 : "+dto.getvMemberId());


//		memberService.modifyMembers(dto);

		return "redirect:/admin/member-list";
	}
}
