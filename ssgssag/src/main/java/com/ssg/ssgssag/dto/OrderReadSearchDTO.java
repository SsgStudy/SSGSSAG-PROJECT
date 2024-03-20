package com.ssg.ssgssag.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReadSearchDTO {
    private String startDate;
    private String endDate;
    private String vIncomingProductSupplierNm;
    private String vWarehouseCd;
    private String vOrderStatus;

    public String getvIncomingProductSupplierNm() {
        return vIncomingProductSupplierNm;
    }

    public String getvWarehouseCd() {
        return vWarehouseCd;
    }

    public String getvOrderStatus() {
        return vOrderStatus;
    }
}
