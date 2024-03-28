package com.ssg.ssgssag.security;

import com.ssg.ssgssag.domain.MemberVO;
import com.ssg.ssgssag.mapper.MemberMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserSecurityService implements UserDetailsService {

	private final MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberVO member = memberMapper.getOneMemberInfo(username);

		if (member.getvMemberId() == null) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		String memberAuth = member.getvMemberAuth();

		if (member.getbIsActive() == 0) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.DEACTIVE.getValue()));
			return new User(member.getvMemberId(), member.getvMemberPw(), authorities);
		}

		if ("ADMIN".equals(memberAuth)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		} else if ("WAREHOUSE_MANAGER".equals(memberAuth)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.WAREHOUSE_MANAGER.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(MemberRole.OPERATOR.getValue()));
		}

		return new User(member.getvMemberId(), member.getvMemberPw(), authorities);
	}

}
