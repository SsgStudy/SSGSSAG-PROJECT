package com.ssg.ssgssag.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingProductVO {
    private Long pkOutgoingId;
    private String vOutgoingStatus;
    private LocalDateTime dtOutgoingDate;
    private int nOutgoingCnt;
    private Long pkShopPurchaseDetailSeq;
    private Long pkShopPurchaseSeq;
    private String vProductCd;
    private String vZoneCd;
    private String vWarehouseCd;
}
