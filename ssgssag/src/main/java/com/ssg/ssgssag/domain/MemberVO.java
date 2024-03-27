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
    private byte[] bProfilePic;
    private String vSocialLoginToken;


    public String getvMemberId() {
        return vMemberId;
    }

    public String getvMemberPw() {
        return vMemberPw;
    }

    public String getvMemberNm() {
        return vMemberNm;
    }

    public String getvMemberAuth() {
        return vMemberAuth;
    }

    public String getvEmail() {
        return vEmail;
    }

    public byte[] getbProfilePic() {
        return bProfilePic;
    }

    public String getvSocialLoginToken() {
        return vSocialLoginToken;
    }


}
