package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {   // 발주조회 시 발주 상세건 조회

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate dtOrderCreatedDate;
    private Long pkOrderDetailSeq;
    private String vOrderType;
    private String vIncomingProductSupplierNm;
    private String vWarehouseCd;

    private LocalDateTime dtDeliveryDate;
    private String vOrderStatus;
    private LocalDateTime dtOrderFinishedDate;
}
