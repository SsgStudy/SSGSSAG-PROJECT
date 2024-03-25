package com.ssg.ssgssag.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {  // 발주 등록 DTO

    // 상태
    private String result;

    // 발주
    private LocalDateTime dtDeliveryDate;
    private Long pkOrderSeq;
    private String vIncomingProductSupplierNm;
    private String vOrderStatus;
    private String vWarehouseCd;
    private String vWarehouseNm;
    private String vOrderType;
    private LocalDate dtOrderCreatedDate;
    private LocalDateTime dtOrderCompletionDate;


    // 발주 상세
    private String vProductCd;
    private String vProductNm;
    private int nProductPrice;
    private String vProductStatus;
    private int nInventoryCnt;
    private int nOrderCnt;
    private int orderTotalPrice;

    public String getvIncomingProductSupplierNm() {
        return vIncomingProductSupplierNm;
    }

    public String getvOrderStatus() {
        return vOrderStatus;
    }

    public String getvWarehouseCd() {
        return vWarehouseCd;
    }

    public String getvWarehouseNm() {
        return vWarehouseNm;
    }

    public String getvOrderType() {
        return vOrderType;
    }

    public String getvProductCd() {
        return vProductCd;
    }

    public String getvProductNm() {
        return vProductNm;
    }

    public int getnProductPrice() {
        return nProductPrice;
    }

    public String getvProductStatus() {
        return vProductStatus;
    }

    public int getnInventoryCnt() {
        return nInventoryCnt;
    }

    public int getnOrderCnt() {
        return nOrderCnt;
    }
}
