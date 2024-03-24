package com.ssg.ssgssag.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovementDTO {
    private Long pkInventorySeq; //해당하는 재고 번호

    @Getter
    private String vWarehouseNm;
    @Getter
    private String vZoneNm;

    public String getvWarehouseNm() {
        return vWarehouseNm;
    }

    public String getvZoneNm() {
        return vZoneNm;
    }
}
