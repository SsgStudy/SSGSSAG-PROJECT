package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.mapper.MemberMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{


    @Autowired
    private final MemberMapper memberMapper;


    @Override
    public void registerMember(MemberVO vo) {

        memberMapper.insertMembers(vo);
    }

    @Override
    public List<MemberDTO> getAllMembers() {

        return memberMapper.selectAllMembers();
    }

    @Override
    public List<MemberDTO> getMembersByName(String name) {
        return memberMapper.selectMemberByName(name);
    }
    @Override
    public List<MemberDTO> getMemberList(MemberDTO memberDTO) {
        return memberMapper.selectMemberByString(memberDTO);
    }

    @Override
    public void modifyMembers(MemberDTO memberDTO) {
       memberMapper.modifyMemberInfo(memberDTO);
    }

}
