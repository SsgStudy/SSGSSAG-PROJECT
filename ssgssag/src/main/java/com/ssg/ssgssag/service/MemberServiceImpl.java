package com.ssg.ssgssag.service;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.dto.MemberDTO;
import com.ssg.ssgssag.mapper.MemberMapper;
import com.ssg.ssgssag.security.DataNotFoundException;
import com.ssg.ssgssag.security.MemberRole;

import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper modelMapper() {
        modelMapper.getConfiguration()
            .setFieldAccessLevel(AccessLevel.PRIVATE)
            .setFieldMatchingEnabled(true);

        return modelMapper;
    }

    @Override
    public void registerMember(MemberDTO member) {
        member.setvMemberPw(passwordEncoder.encode(member.getvMemberPw()));
        MemberVO memberVO = modelMapper.map(member, MemberVO.class);
        log.info("registerMember {}", memberVO);

        memberMapper.insertMembers(memberVO);
    }


    @Override
    public MemberVO getMemberByMemberId(String memberId) {
        MemberVO member = memberMapper.getOneMemberInfo(memberId);
        if (member == null) {
            throw new DataNotFoundException("member not found");
        }
        return member;
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public List<MemberDTO> getMemberList(MemberDTO memberDTO) {
        return memberMapper.selectMemberByString(memberDTO);
    }

    @Override
    public MemberVO getOneMemberInModal(String memberId) {
        return memberMapper.getOneMemberInfo(memberId);
    }

    //총관리자가 회원 수정
    @Override
    public void modifyMembersByAdmin(MemberDTO memberDTO) {
        MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);
        memberMapper.updateMembersByMemberId(memberVO);
    }


    @Override
    public void modifyMemberInfo(MemberDTO memberDTO) {
        MemberVO vo = modelMapper.map(memberDTO, MemberVO.class);
        memberMapper.updateMemberInfo(vo);
    }

    @Override
    public boolean checkIdInfo(String vMemberId) {
        return memberMapper.checkId(vMemberId);
    }


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        MemberVO memberVO = memberMapper.getOneMemberInfo(memberId);

        if (memberVO == null) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ADMIN".equals(memberVO.getvMemberAuth())) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } if ("WAREHOUSE_MANAGER".equals(memberVO.getvMemberAuth())) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.WAREHOUSE_MANAGER.getValue()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.OPERATOR.getValue()));
        }
        return new User(memberVO.getvMemberId(), memberVO.getvMemberPw(), authorities);
    }

    @Override
    public MemberVO login(MemberDTO memberDTO) {
        return memberMapper.login(memberDTO.getvMemberId(),
            passwordEncoder.encode(memberDTO.getvMemberPw()));

    }

    @Override
    public int modifyPassword(MemberDTO member) {
        int result = memberMapper.selectOneMemberByMemberIdAndMemberPw(member);
        if (result == 1) {
            memberMapper.updateMemberPassword(member);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public byte[] getMemberProfileImg(String vMemberId) {
        MemberVO member = memberMapper.selectMemberProfileImgByMemberId(vMemberId);
        return member.getbProfilePic();

    public Boolean deleteMember(MemberDTO memberDTO) {
        try {
            MemberVO memberVO = modelMapper.map(memberDTO, MemberVO.class);
            memberMapper.deleteMemberInfo(memberVO);
            SecurityContextHolder.clearContext();
            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
