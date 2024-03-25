package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    void insertMembers(MemberVO memberVO);

    List<MemberDTO> selectAllMembers();

    List<MemberDTO> selectMemberByName(@Param("name") String name);

    List<MemberDTO> selectMemberByString(MemberDTO member);

    void modifyMemberInfo(MemberDTO memberDTO);

    MemberVO getOneMemberInfo(String memberId);

    boolean checkId(String vMemberId);
    MemberVO login(@Param("vMemberId") String memberId, @Param("vMemberPw") String memberPw);
}
