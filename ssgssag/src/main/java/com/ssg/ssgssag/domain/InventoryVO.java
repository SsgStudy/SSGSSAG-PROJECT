package com.ssg.ssgssag.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventoryVO {
    private Integer pkInventorySeq;
    private LocalDateTime dtInventorySlipDate;
    private Integer nInventoryCnt;
    private String vZoneCd;
    private String vWarehouseCd;
    private String vProductCd;
}
