package com.ssg.ssgssag.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {  // 발주 등록 DTO

    // 발주
    private LocalDateTime dtDeliveryDate;
    private Long pkoOrderSeq;
    private String vIncomingProductSupplierNm;
    private String vOrderStatus;
    private String vWarehouseCd;
    private String vOrderType;

    // 발주 상세
    private String vProductCd;
    private String vProductNm;
    private int nProductPrice;
    private String vProductStatus;
    private int nInventoryCnt;
    private int nOrderCnt;
}
