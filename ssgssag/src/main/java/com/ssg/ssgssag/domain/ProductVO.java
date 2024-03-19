package com.ssg.ssgssag.domain;

import lombok.*;

import java.sql.Date;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private String vProductCd;
    private String vProductNm;
    private int nProductPrice;
    private String vProductBrand;
    private String vProductOrign;
    private String vManufactor;
    private String vPrductStatus;
    private Date dProductManufactorsDate;
    private String vCategoryCode;
}
