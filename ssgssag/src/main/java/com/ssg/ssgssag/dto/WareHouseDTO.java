package com.ssg.ssgssag.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseDTO {
    private String vWarehouseCd;
    private String vWarehouseNm;
    private String vWarehouseLoc;
    private String sWarehouseType;
    private Long pkMemberSeq;
}
