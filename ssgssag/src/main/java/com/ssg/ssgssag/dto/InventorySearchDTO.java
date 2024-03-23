package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventorySearchDTO {
    private String vMainCategoryNm;
    private String vSubCategoryNm;
    private String vDetailCategoryNm;
    private String vWarehouseNm;
    private String vZoneNm;

    public String getvMainCategoryNm() {
        return vMainCategoryNm;
    }

    public String getvSubCategoryNm() {
        return vSubCategoryNm;
    }

    public String getvDetailCategoryNm() {
        return vDetailCategoryNm;
    }

    public String getvWarehouseNm() {
        return vWarehouseNm;
    }

    public String getvZoneNm() {
        return vZoneNm;
    }
}
