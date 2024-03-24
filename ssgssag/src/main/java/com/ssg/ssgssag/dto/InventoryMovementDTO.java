package com.ssg.ssgssag.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovementDTO {
    private Long pkInventorySeq; //해당하는 재고 번호

    @Getter
    private String vWarehouseNm2;
    @Getter
    private String vZoneNm2;

    public String getvWarehouseNm2() {
        return vWarehouseNm2;
    }

    public String getvZoneNm2() {
        return vZoneNm2;
    }
}
