package com.ssg.ssgssag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestProductDTO {

    private String name;
    private int purchase;
    private int count;
    private int inventory;

}
