package com.ssg.ssgssag.domain;

import lombok.*;

import java.sql.Blob;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private Long pkMemberSeq;
    private String vMemberId;
    private String vMemberPw;
    private String vMemberNm;
    private String vMemberAuth;
    private String vEmail;
    private Blob bProfilePic;
    private String vSocialLoginToken;
}
