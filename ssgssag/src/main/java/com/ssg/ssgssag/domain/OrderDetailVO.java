package com.ssg.ssgssag.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {
    private Long pkOrderDetailSeq;
    private Long pkOrderSeq;
    private int nOrderCnt;
    private String vOrderStatus;
    private String vProductCd;
    private String vWarehouseCd;
    private LocalDateTime dtOrderFinishedDate;
}
