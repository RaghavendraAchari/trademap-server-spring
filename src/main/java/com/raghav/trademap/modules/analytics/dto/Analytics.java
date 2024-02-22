package com.raghav.trademap.modules.analytics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Analytics {
    private Long useId; //for future reference

    private Double totalInvestment;

    private Integer totalDays;
    private Integer totalWeekends;
    private Integer totalNoTradingDays;
    private Integer totalHolidays;

    private Integer totalTrades;
    private Integer totalTradesInStock;
    private Integer totalTradesInIndex;
    private Integer totalTradesInCommodity;

    private Double totalPnl;
    private Double maxProfitInADay;
    private Double maxLossInADay;

    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss")
    private LocalDate dateOfMaxProfit;
    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss")
    private LocalDate dateOfMaxLoss;

    private Integer noOfProfitTrades;
    private Integer noOfLossTrades;
}
