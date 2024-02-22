package com.raghav.trademap.modules.settings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class SettingsResponse {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate trackingDate;

    private Integer maxTradesLimit;
    private Boolean warnWhenMaxLimitReached;

    public static SettingsResponse mapToSettingsResponse(com.raghav.trademap.modules.settings.Settings settings){
        return SettingsResponse.builder()
                .id(settings.getId())
                .maxTradesLimit(settings.getMaxTradeLimit())
                .warnWhenMaxLimitReached(settings.getWarnOnMaxTrade())
                .trackingDate(settings.getStartDate())
                .build();
    }
}
