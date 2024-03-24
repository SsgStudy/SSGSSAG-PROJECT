package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryListDTO {
    private Integer pkInventorySeq;
    private LocalDateTime dtInventorySlipDate;
    private Integer nInventoryCnt;
    private String vZoneCd;
    private String vWarehouseCd;
    private String vWarehouseNm;
    private String vProductCd;
    private String vProductNm;
}
