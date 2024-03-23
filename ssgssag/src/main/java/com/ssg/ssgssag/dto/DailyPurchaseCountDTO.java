package com.ssg.ssgssag.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyPurchaseCountDTO {

    private Date purchaseDate;
    private int dailyPurchaseDate;
}
