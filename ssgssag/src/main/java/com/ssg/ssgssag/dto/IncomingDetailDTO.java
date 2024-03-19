package com.ssg.ssgssag.dto;

import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingDetailDTO {

    private long pkIncomingProductSeq;
    private String vIncomingProductStatus;
    private LocalDateTime dtIncomingProductDate;
    private String vIncomingProductType;
    private String vIncomingProductSupplierNm;
    private int nIncomingProductCnt;
    private int nIncomingProductPrice;
    private String vProductCd;
    private String vWarehouseCd;
    private String vZoneCd;
    private String vProductNm;
    private int nProductPrice;
    private String vProductBrand;
    private String vProductOrigin;
    private String vProductManufactor;
    private String vProductStatus;
    private Date dProductManufactorDate;
    private String vCategoryCd;
}
