package com.raghav.trademap.modules.insights.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.raghav.trademap.modules.insights.model.Insight;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InsightResponse {
    private Long id ;

    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdatedDateTime;

    @JsonFormat(pattern = "YYYY-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDateTime;

    private String title;

    private String content;

    private String insightType;

    public static InsightResponse mapToInsightResponse(Insight data){
        return new InsightResponse(data.getId(),data.getLastUpdatedDateTime(), data.getCreatedDateTime() , data.getTitle(), data.getContent(), data.getInsightType().name());
    }
}
