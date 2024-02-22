package com.raghav.trademap.modules.analytics;

import com.raghav.trademap.modules.analytics.dto.AnalyticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    AnalyticsService analyticsService;

    public ResponseEntity<AnalyticsResponse> getDetails(){
        return ResponseEntity.ok(analyticsService.getDetails(null));
    }
}
