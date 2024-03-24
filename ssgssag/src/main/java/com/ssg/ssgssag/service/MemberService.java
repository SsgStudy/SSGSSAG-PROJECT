package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;

public interface MemberService {

    void registerMember(MemberVO memberVO);

    List<MemberDTO> getAllMembers();

    List<MemberDTO> getMembersByName(String name);

    List<MemberDTO> getMemberList(MemberDTO memberDTO);

    MemberVO getOneMemberInModal(String getMemberId);

    void modifyMembers(MemberDTO dto);

}
