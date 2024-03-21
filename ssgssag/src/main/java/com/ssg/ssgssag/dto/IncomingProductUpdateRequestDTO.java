package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomingProductUpdateRequestDTO {

    private String pkIncomingProductSeq;
    private String dtIncomingProductDate;
    private String vzoneCd;
}
