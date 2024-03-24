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
    private Blob bProfilePic;
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

    public Blob getbProfilePic() {
        return bProfilePic;
    }

    public String getvSocialLoginToken() {
        return vSocialLoginToken;
    }
}
