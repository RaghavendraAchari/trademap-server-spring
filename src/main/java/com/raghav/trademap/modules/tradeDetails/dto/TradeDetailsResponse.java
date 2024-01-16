package com.raghav.trademap.modules.tradeDetails.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.raghav.trademap.common.types.InstrumentType;
import com.raghav.trademap.modules.tradeDetails.TradeDetails;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TradeDetailsResponse {

    private long id;

    private Integer day;

    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private boolean noTradingDay;

    @JsonProperty("isHoliday")
    private boolean isHoliday;

    @JsonProperty("isWeekend")
    private Boolean isWeekend;

    private InstrumentType instrumentType;

    private String instrumentName;

    private String setupName;

    private String remarks;

    private float riskToReward;

    private float riskToRewardOnPremium;

    private double pnl;

    private String resultType;

    private List<String> imagePaths;

    public static TradeDetailsResponse mapToTradeDetailsResponse(TradeDetails tradeDetails){
        return TradeDetailsResponse.builder()
                .id(tradeDetails.getId())
                .day(tradeDetails.getDay())
                .noTradingDay(tradeDetails.isNoTradingDay())
                .isHoliday(tradeDetails.isHoliday())
                .dateTime(tradeDetails.getDateTime())
                .instrumentType(tradeDetails.getInstrumentType())
                .instrumentName(tradeDetails.getInstrumentName())
                .setupName(tradeDetails.getSetupName())
                .riskToRewardOnPremium(tradeDetails.getRiskToRewardOnPremium())
                .riskToReward(tradeDetails.getRiskToReward())
                .remarks(tradeDetails.getRemarks())
                .pnl(tradeDetails.getPnl())
                .resultType(tradeDetails.getResultType())
                .imagePaths(tradeDetails.getImagePaths())
                .isWeekend(tradeDetails.getIsWeekend())
                .build();
    }
}
