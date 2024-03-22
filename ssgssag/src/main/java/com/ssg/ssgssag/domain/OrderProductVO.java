package com.ssg.ssgssag.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductVO {
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
    private int orderTotalPrice;
}
