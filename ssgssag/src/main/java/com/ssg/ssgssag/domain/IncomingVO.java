package com.ssg.ssgssag.domain;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IncomingVO {
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
