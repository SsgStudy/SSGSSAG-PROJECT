package com.ssg.ssgssag.domain;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private Long pkoOrderSeq;
    private String vOrderStatus;
    private String vIncomingProductSupplierNm;
    private String vOrderType;
    private LocalDateTime dtOrderCreatedDate;
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
