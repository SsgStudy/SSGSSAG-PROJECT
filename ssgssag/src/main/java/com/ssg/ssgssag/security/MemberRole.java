package com.ssg.ssgssag.security;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ADMIN"),
    WAREHOUSE_MANAGER("WAREHOUSE_MANAGER"),
    OPERATOR("OPERATOR"),
    DEACTIVE("DEACTIVE");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}
