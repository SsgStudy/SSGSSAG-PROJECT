package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    void insertMembers(MemberVO memberVO);

    List<MemberDTO> selectAllMembers();

    List<MemberDTO> selectMemberByName(@Param("name") String name);

    List<MemberDTO> selectMemberByString(MemberDTO member);

//    관리자
    void updateMembersByMemberId(MemberVO memberVO);

    void updateMemberInfo(MemberVO memberVO);

    MemberVO getOneMemberInfo(String memberId);

    boolean checkId(String vMemberId);
    MemberVO login(@Param("vMemberId") String memberId, @Param("vMemberPw") String memberPw);

    int selectOneMemberByMemberIdAndMemberPw(MemberDTO member);

    void updateMemberPassword(MemberDTO member);
}
