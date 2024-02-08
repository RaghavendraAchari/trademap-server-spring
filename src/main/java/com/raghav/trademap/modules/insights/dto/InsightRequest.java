package com.raghav.trademap.modules.insights.dto;

import com.raghav.trademap.modules.insights.model.InsightType;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsightRequest {
    private Long id = null;
    private String title;
    private String content;
    private InsightType insightType = InsightType.INSIGHT;
}
