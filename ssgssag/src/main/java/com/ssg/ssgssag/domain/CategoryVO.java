package com.ssg.ssgssag.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {
    private String vCategoryCd;
    private String vCategoryNm;
    private String vCategoryParentCd;
}
