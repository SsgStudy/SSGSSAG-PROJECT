package com.ssg.ssgssag.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String vProductCd;
    private String vProductName;
    private int nProductPrice;
    private String vProductBrand;
    private String vProductOrigin;
    private String vProductManufacturer;
    private String vProductStatus;
    private Date dProductManufactureDate;
    private String vCategoryCd;
}
