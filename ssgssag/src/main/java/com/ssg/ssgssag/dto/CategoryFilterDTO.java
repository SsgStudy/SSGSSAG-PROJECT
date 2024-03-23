package com.ssg.ssgssag.dto;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFilterDTO {
    //카테고리
    private String vMainCategoryNm;
    private String vSubCategoryNm;
    private String vDetailCategoryNm;

}
