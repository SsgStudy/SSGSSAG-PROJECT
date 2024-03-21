package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseZoneDTO {

    //창고
    private String vWarehouseNm;
    private String vZoneNm;
}