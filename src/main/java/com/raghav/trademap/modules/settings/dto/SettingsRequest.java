package com.raghav.trademap.modules.settings.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingsRequest {
    @NotNull
    private LocalDate trackingDate;

    @NotNull
    @Min(1)
    private Integer maxTradesLimit;

    @NotNull
    private Boolean warnWhenMaxLimitReached;
}
