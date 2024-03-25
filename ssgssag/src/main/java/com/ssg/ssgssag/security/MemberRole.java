package com.ssg.ssgssag.security;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ADMIN"),
    WAREHOUSE_MANAGER("WAREHOUSE_MANAGER"),
    OPERATOR("OPERATOR");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}
