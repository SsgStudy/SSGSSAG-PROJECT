package com.ssg.ssgssag.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventoryVO {
    private Integer pkInventorySeq;
    private String vInventorySlipDate;
    private Integer nInventoryCnt;
    private String vZoneCd;
    private String vWarehouseCd;
    private String vProductCd;
}
