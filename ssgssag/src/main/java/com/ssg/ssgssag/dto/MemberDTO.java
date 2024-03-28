package com.ssg.ssgssag.dto;

import java.sql.Blob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MemberDTO {
    private Long pkMemberSeq;
    private String vMemberId;
    private String vMemberPw;
    private String vMemberNm;
    private String vMemberAuth;
    private String vEmail;
    private byte[] bProfilePic;
    private String vSocialLoginToken;
    private int bIsActive;
    private String vMemberNewPw;


    public int getbIsActive() {
        return bIsActive;
    }

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

    public void setvMemberId(String vMemberId) {
        this.vMemberId = vMemberId;
    }

    public void setvMemberPw(String vMemberPw) {
        this.vMemberPw = vMemberPw;
    }

    public void setvMemberNm(String vMemberNm) {
        this.vMemberNm = vMemberNm;
    }

    public void setvMemberAuth(String vMemberAuth) {
        this.vMemberAuth = vMemberAuth;
    }

    public void setvEmail(String vEmail) {
        this.vEmail = vEmail;
    }

    public void setbProfilePic(byte[] bProfilePic) {
        this.bProfilePic = bProfilePic;
    }

    public void setbIsActive(int bIsActive) {
        this.bIsActive = bIsActive;
    }

    public void setvSocialLoginToken(String vSocialLoginToken) {
        this.vSocialLoginToken = vSocialLoginToken;
    }
}
