package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

}
