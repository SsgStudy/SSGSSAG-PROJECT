package com.ssg.ssgssag.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomingDTO {

    private long pkIncomingProductSeq;
    private String vIncomingProductStatus;
    private LocalDateTime dtIncomingProductDate;
    private String vIncomingProductType;
    private String vIncomingProductSupplierNm;
    private int nIncomingProductCnt;
    private int nIncomingProductPrice;
    private String vProductCd;
    private String vWarehouseCd;
    private String vZoneCd;
}
