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

    public int getnOrderCnt() {
        return nOrderCnt;
    }

    public String getvOrderStatus() {
        return vOrderStatus;
    }

    public String getvProductCd() {
        return vProductCd;
    }

    public String getvWarehouseCd() {
        return vWarehouseCd;
    }
}
