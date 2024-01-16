package com.raghav.trademap.modules.tradeDetails.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoTradeRequest {
    private LocalDateTime dateTime;

    @JsonProperty("isHoliday")
    private Boolean isHoliday = false;

    @JsonProperty("isWeekend")
    private Boolean isWeekend = false;

    private Boolean noTradingDay = false;

    private String remarks;
}
