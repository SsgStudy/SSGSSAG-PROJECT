package com.ssg.ssgssag.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WareHouseVO {
    private String vWarehouseCd;
    private String vWarehouseNm;
    private String vWarehouseLoc;
    private String vWarehouseType;
    private Long pkMemberSeq;
}
