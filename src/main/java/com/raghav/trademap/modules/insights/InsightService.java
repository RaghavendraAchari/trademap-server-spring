package com.raghav.trademap.modules.insights;

import com.raghav.trademap.common.types.ResourceType;
import com.raghav.trademap.exceptions.ResourceNotFoundException;
import com.raghav.trademap.modules.insights.dto.InsightRequest;
import com.raghav.trademap.modules.insights.dto.InsightResponse;
import com.raghav.trademap.modules.insights.dto.InsightsWithOnlyTitle;
import com.raghav.trademap.modules.insights.model.Insight;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InsightService {
    private final InsightsRepo insightsRepo;

    public InsightService(InsightsRepo repo){
        this.insightsRepo = repo;
    }

    public List<InsightResponse> getAllInsights() {
        List<Insight> result = insightsRepo.findAll(Sort.by(Sort.Direction.DESC, "createdDateTime"));

        return result.stream().map(InsightResponse::mapToInsightResponse).toList();
    }

    public List<InsightsWithOnlyTitle> getAllInsightsWithTitlesOnly() {
        List<Insight> result = insightsRepo.findAll(Sort.by(Sort.Direction.DESC, "createdDateTime"));

        return result.stream().map(InsightsWithOnlyTitle::mapToInsightsWithTitlesOnly).toList();
    }

    public InsightResponse getInsightById(Long id) {
        Optional<Insight> result = insightsRepo.findById(id);
        Insight insight = result.orElseThrow(() -> new ResourceNotFoundException("No resource found", ResourceType.INSIGHT));

        return InsightResponse.mapToInsightResponse(insight);
    }

    @Transactional
    public InsightResponse saveInsight(InsightRequest request){
        Insight insight = Insight.fromRequest(request);

        Insight saved = insightsRepo.save(insight);

        if (saved.getId() == null)
            throw new RuntimeException("Could not save the data");

        return InsightResponse.mapToInsightResponse(saved);
    }

    @Transactional
    public InsightResponse updateInsight(InsightRequest request) {
        Optional<Insight> result = insightsRepo.findById(request.getId());
        Insight oldData = result.orElseThrow();

        oldData.setContent(request.getContent());
        oldData.setTitle(request.getTitle());
        oldData.setLastUpdatedDateTime(LocalDateTime.now());

        Insight saved = insightsRepo.save(oldData);

        return InsightResponse.mapToInsightResponse(saved);
    }

    @Transactional
    public Boolean deleteInsight(Long id) {
        insightsRepo.deleteById(id);

        Optional<Insight> result = insightsRepo.findById(id);

        return result.isEmpty();
    }


}
