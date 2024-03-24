package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryShortageDTO {
    private int totalInventoryCount;
    private String vWarehouseCd;
    private String vProductCd;
    private String vProductNm;
}
