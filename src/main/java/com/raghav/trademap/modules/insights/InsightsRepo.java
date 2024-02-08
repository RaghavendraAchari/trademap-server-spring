package com.raghav.trademap.modules.insights;

import com.raghav.trademap.modules.insights.model.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightsRepo extends JpaRepository<Insight, Long> {

}
