package com.ssg.ssgssag.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryAdjustmentDTO {
    private Long pkInventorySeq; //해당하는 재고 번호

    @Getter
    private String vInventoryChangeType; //변경 구분 타입 입고 or 출고
    @Getter
    private int nInventoryShippingCnt; //변동 수량

    public String getvInventoryChangeType() {
        return vInventoryChangeType;
    }

    public int getnInventoryShippingCnt() {
        return nInventoryShippingCnt;
    }
}
