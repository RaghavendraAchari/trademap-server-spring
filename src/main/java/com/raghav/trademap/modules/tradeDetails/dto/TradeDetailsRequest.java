package com.raghav.trademap.modules.tradeDetails.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.raghav.trademap.common.types.InstrumentType;
import com.raghav.trademap.modules.tradeDetails.TradeDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TradeDetailsRequest {
        private Integer day;

        @NotNull
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime dateTime;

        @NotNull
        @JsonProperty("noTradingDay")
        private boolean noTradingDay;

        @NotNull
        @JsonProperty("isHoliday")
        private boolean isHoliday;

        @NotNull
        @JsonProperty("isWeekend")
        private boolean isWeekend;

        @Valid
        private InstrumentType instrumentType;

        @NotBlank
        private String instrumentName;

        @NotBlank
        private String setupName;

        private Float riskToReward;

        private Float riskToRewardOnPremium;

        @NotBlank
        private String remarks;

        @NotBlank
        private String resultType;

        @NotNull
        private Double pnl;

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
                        .isWeekend(request.isWeekend)
                        .remarks(request.remarks)
                        .setupName(request.setupName)
                        .resultType(request.resultType)
                        .riskToReward(request.riskToReward)
                        .riskToRewardOnPremium(request.riskToRewardOnPremium)
                        .build();
        }
}
