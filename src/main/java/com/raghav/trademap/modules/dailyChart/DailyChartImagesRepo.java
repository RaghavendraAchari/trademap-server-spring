package com.raghav.trademap.modules.dailyChart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyChartImagesRepo extends JpaRepository<DailyChartImages, Long> {
//    Page<DailyChartImages> findLastByDate(Pageable pageable);
}
