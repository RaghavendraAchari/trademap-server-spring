package com.raghav.trademap.requestResponseModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raghav.trademap.model.InstrumentType;
import com.raghav.trademap.model.TradeDetails;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

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

    private boolean isHoliday;

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
                .build();
    }
}
