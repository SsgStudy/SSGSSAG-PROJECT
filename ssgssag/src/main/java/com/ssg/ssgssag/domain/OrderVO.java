package com.ssg.ssgssag.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private Long pkOrderSeq;
    private String vOrderStatus;
    private String vIncomingProductSupplierNm;
    private String vOrderType;
    private LocalDate dtOrderCreatedDate;
    private LocalDateTime dtDeliveryDate;
    private LocalDateTime dtOrderCompletionDate;

    public String getvOrderStatus() {
        return vOrderStatus;
    }

    public String getvIncomingProductSupplierNm() {
        return vIncomingProductSupplierNm;
    }

    public String getvOrderType() {
        return vOrderType;
    }
}
