package com.raghav.trademap.repo;

import com.raghav.trademap.model.TrackingDateDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingDateDetailsRepo extends JpaRepository<TrackingDateDetails, Integer> {
}
