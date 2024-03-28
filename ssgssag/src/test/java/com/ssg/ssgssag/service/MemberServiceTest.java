package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired(required = false)
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testRegisterMember() {
        MemberDTO memberDTO= MemberDTO.builder()
            .vMemberId("sowon")
            .vMemberPw("990901ok")
            .vMemberNm("최소원")
            .vEmail("sowon901@naver.com")
            .build();

        memberService.registerMember(memberDTO);
    }

    @Test
    public void testListMember() {
            List<MemberDTO> memberDTOList = memberService.getAllMembers();

            log.info(memberDTOList);
    }

    @Test
    public void testListByName() {
        String name = "한예슬";
        log.info(memberService.getMembersByName(name));
    }

    @Test
    public void testFilters() {
        // 이름 검색
        MemberDTO memberDTO = MemberDTO.builder()
            .vMemberAuth("admin")
            .vMemberNm("조민수")
            .build();

        memberService.getMemberList(memberDTO).forEach(System.out::println);

        MemberDTO memberDTO2 = MemberDTO.builder()
            .vMemberAuth("admin")
            .vMemberId("admin001")
            .build();

        memberService.getMemberList(memberDTO2).forEach(System.out::println);

        MemberDTO memberDTO3 = MemberDTO.builder()
            .vMemberAuth("admin")
            .vEmail("aa")
            .build();

        memberService.getMemberList(memberDTO3).forEach(System.out::println);

        MemberDTO memberDTO4 = MemberDTO.builder()
            .vMemberAuth("admin")
            .vMemberNm("조민수")
            .vEmail("aa")
            .build();

        memberService.getMemberList(memberDTO4).forEach(System.out::println);

        MemberDTO memberDTO5 = MemberDTO.builder()
            .vMemberAuth("admin")
            .vMemberId("admin001")
            .vMemberNm("조민수")
            .vEmail("aa")
            .build();

        memberService.getMemberList(memberDTO5).forEach(System.out::println);


    }


    @Test
    public void testModifyMember() {
        MemberDTO memberDTO = MemberDTO.builder()
            .vMemberId("w").vMemberNm("안녕").vMemberPw("12345").vEmail("sowon901@naver.com")
            .vMemberAuth("ADMIN").build();


//       memberService.modifyMembers(memberDTO);
    }

    @Test
    public void testModifyMemberByMember() {
        String pw = "$2a$10$sk7k38u0RJgLGqcySwotg.OT5iy8Rvu4oCPVu2mHNKNYETCqzvkN2";


        MemberDTO memberDTO = MemberDTO.builder()
            .vMemberId("test03252")
            .vMemberPw(pw)
            .vEmail("modify@naver.com")
            .build();

       memberService.modifyMemberInfo(memberDTO);
    }

    @Test
    public void testGet() {
        log.info(memberService.getOneMemberInModal("admin001"));
    }


    @Test
    public void testId() {

        if(memberService.checkIdInfo("test03252")) {
            log.info("회원존재");
        }
        else
            log.info("회원 존재하지 않음");

        log.info("id 정보" , memberService.checkIdInfo(""));

    }

    @Test
    public void loginTest() {

        MemberDTO memberDTO = MemberDTO.builder()
                .vMemberId("test0325")
                    .vMemberPw("12345")
                     .build();

        memberService.login(memberDTO);

        log.info(memberService.login(memberDTO));
    }

    @Test
    public void testFindOneMember() {
        MemberDTO memberDTO= MemberDTO.builder()
            .vMemberId("test0325")
            .vMemberPw("12345")
            .build();

        memberService.getMemberByMemberId(memberDTO.getvMemberId());
    }

    @Test
    public void testAdminModify() {

        MemberDTO memberDTO = MemberDTO.builder()
            .vMemberId("ventis")
            .vMemberPw("111")
            .vMemberNm("더벤티")
            .vMemberAuth("ADMIN")
                .vEmail("ventis@google.com").build();
        memberService.modifyMembersByAdmin(memberDTO);

        log.info(memberDTO);

    }

    @Test
    public void modifyMemberPasswordTest() {
        MemberDTO memberDTO = MemberDTO.builder().vMemberId("test0327")
                .vMemberPw("12345")
                .vMemberNewPw("123456")
                .build();

        memberService.modifyPassword(memberDTO);


    }
}
