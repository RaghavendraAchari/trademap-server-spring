package com.raghav.trademap.modules.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class SettingsResponse {
    private LocalDate trackingDate;
    private Integer maxTradesLimit;
    private Boolean warnWhenMaxLimitReached;

    public static SettingsResponse mapToSettingsResponse(com.raghav.trademap.modules.settings.Settings settings){
        return SettingsResponse.builder()
                .maxTradesLimit(settings.getMaxTradeLimit())
                .warnWhenMaxLimitReached(settings.getWarnOnMaxTrade())
                .trackingDate(settings.getStartDate())
                .build();
    }
}
