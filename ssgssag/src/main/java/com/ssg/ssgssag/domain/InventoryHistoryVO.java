package com.ssg.ssgssag.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InventoryHistoryVO {
    private Long pkInventorySeq;
    private int nInventoryShippingCnt;
    private LocalDateTime dtInventoryChangeDate;
    private String vInventoryChanegeType;
    private String vZoneCd;
    private String vZoneCd2;
    private String vWarehouseCd;
    private String vWarehouseCd2;


}
