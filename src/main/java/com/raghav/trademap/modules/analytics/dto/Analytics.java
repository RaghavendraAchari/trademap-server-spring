package com.raghav.trademap.modules.analytics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raghav.trademap.modules.tradeDetails.dataClasses.DateAndProfit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private Integer totalTradedDays;

    private Integer totalTrades;
    private Integer totalTradesInStock;
    private Integer totalTradesInIndex;
    private Integer totalTradesInCommodity;

    private Double totalPnl;
    private Double maxProfitInADay;
    private Double maxLossInADay;

    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateOfMaxProfit;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateOfMaxLoss;

    private Integer noOfProfitTrades;
    private Integer noOfLossTrades;

    private Integer profitMakingDays;
    private Integer lossMakingDays;

    private List<DateAndProfit> dateWiseProfit;
}
