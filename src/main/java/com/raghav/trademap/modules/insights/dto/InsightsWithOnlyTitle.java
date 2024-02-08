package com.raghav.trademap.modules.insights.dto;

import com.raghav.trademap.modules.insights.model.Insight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsightsWithOnlyTitle {
    private Long id;
    private String title;
    private String insightType;
    private LocalDateTime dateTime;

    public static InsightsWithOnlyTitle mapToInsightsWithTitlesOnly(Insight data){
        return new InsightsWithOnlyTitle(data.getId(),data.getTitle(), data.getInsightType().name(), data.getCreatedDateTime());
    }
}
