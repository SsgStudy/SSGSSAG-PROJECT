package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@SpringBootTest

public class MemberServiceTest {

    @Autowired(required = false)
    private MemberService memberService;
    @Test
    public void testRegisterMember() {
        MemberVO memberVO= MemberVO.builder()
            .vMemberId("sowon")
            .vMemberPw("990901ok")
            .vMemberNm("최소원")
            .vEmail("sowon901@naver.com")
            .vMemberAuth("일반회원")
            .build();

        memberService.registerMember(memberVO);
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
    public void testModify() {
        MemberDTO memberDTO = MemberDTO.builder()
            .vMemberId("w").vMemberNm("안녕").vMemberPw("12345").vEmail("sowon901@naver.com")
            .vMemberAuth("ADMIN").build();


       memberService.modifyMembers(memberDTO);
    }

    @Test
    public void testGet() {
        log.info(memberService.getOneMemberInModal("admin001"));
    }

}
