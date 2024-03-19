package com.ssg.ssgssag.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingInstVO {
    private Long pkShopPurchaseSeq;
    private String vOutgoingInstStatus;
}
