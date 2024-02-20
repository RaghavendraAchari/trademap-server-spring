package com.raghav.trademap.modules.insights.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InsightUpdateRequest {
    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String content;
}
