package com.raghav.trademap.modules.insights.model;

import com.raghav.trademap.modules.insights.dto.InsightRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "insight")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = true)
    private LocalDateTime lastUpdatedDateTime;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "insight_type")
    private InsightType insightType;

    public static Insight fromRequest(InsightRequest request){
        return new Insight(null, LocalDateTime.now(), null, request.getTitle(),
                request.getContent(), request.getInsightType());
    }
}
