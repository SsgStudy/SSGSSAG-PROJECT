package com.ssg.ssgssag.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    private Long pkOrderDetailSeq;
    private Long pkOrderSqe;
    private int nOrderCnt;
    private String vOrderStatus;
    private String vProductCd;
    private String vWarehouseCd;
}
