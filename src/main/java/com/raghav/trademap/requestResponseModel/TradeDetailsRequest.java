package com.raghav.trademap.requestResponseModel;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.raghav.trademap.model.InstrumentType;
import com.raghav.trademap.model.TradeDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TradeDetailsRequest {
        private Integer day;

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-DD'T'HH:mm:ss")
        private LocalDateTime dateTime;

        @NotNull
        private boolean noTradingDay;

        @NotNull
        private boolean isHoliday;

        @NotBlank
        private InstrumentType instrumentType;

        @NotBlank
        private String instrumentName;

        @NotBlank
        private String setupName;

        @NotBlank
        private float riskToReward;

        private float riskToRewardOnPremium;

        @NotBlank
        private String remarks;

        @NotBlank
        private String resultType;

        @NotBlank
        private double pnl;

        public static TradeDetails mapToTradeDetails(TradeDetailsRequest request, List<String> paths) {
                return TradeDetails.builder()
                        .dateTime(request.dateTime)
                        .day(request.getDay())
                        .noTradingDay(request.isNoTradingDay())
                        .pnl(request.getPnl())
                        .imagePaths(paths)
                        .instrumentName(request.instrumentName)
                        .instrumentType(request.instrumentType)
                        .isHoliday(request.isHoliday)
                        .remarks(request.remarks)
                        .setupName(request.setupName)
                        .resultType(request.resultType)
                        .riskToReward(request.riskToReward)
                        .riskToRewardOnPremium(request.riskToRewardOnPremium)
                        .build();
        }
}
