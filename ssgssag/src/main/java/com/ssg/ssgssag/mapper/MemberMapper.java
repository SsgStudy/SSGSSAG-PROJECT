package com.ssg.ssgssag.mapper;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {


    void insertMembers(MemberVO memberVO);

    List<MemberDTO> selectAllMembers();

}
