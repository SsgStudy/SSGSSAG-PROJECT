package com.ssg.ssgssag.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String vProductCd;
    private String vProductNm;
    private int nProductPrice;
    private String vProductBrand;
    private String vProductOrigin;
    private String vProductManufactor;
    private String vProductStatus;
    private Date dProductManufactorDate;
    private String vCategoryCd;
}
