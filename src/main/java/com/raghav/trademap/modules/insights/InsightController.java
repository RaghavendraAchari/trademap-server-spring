package com.raghav.trademap.modules.insights;

import com.raghav.trademap.modules.insights.dto.InsightRequest;
import com.raghav.trademap.modules.insights.dto.InsightResponse;
import com.raghav.trademap.modules.insights.dto.InsightsWithOnlyTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insights")
public class InsightController {

    @Autowired
    private InsightService insightService;

    @GetMapping()
    public ResponseEntity<List<InsightResponse>> getInsights(){
        return ResponseEntity.ok(insightService.getAllInsights());
    }

    @GetMapping("/onlyTitles")
    public ResponseEntity<List<InsightsWithOnlyTitle>> getInsightsWithTitlesOnly(){
        return ResponseEntity.ok(insightService.getAllInsightsWithTitlesOnly());
    }

    @GetMapping("/:id")
    public ResponseEntity<InsightResponse> getInsightById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(insightService.getInsightById(id));
    }

    @PostMapping()
    public ResponseEntity<InsightResponse> saveInsights(@RequestBody InsightRequest request){
        return ResponseEntity.ok(insightService.saveInsight(request));
    }

    @PutMapping()
    public ResponseEntity<InsightResponse> updateInsights(@RequestBody InsightRequest request){
        if(request.getId() == null)
            throw new RuntimeException("ID must not be null");

        return ResponseEntity.ok(insightService.updateInsight(request));
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> deleteInsights(@RequestParam(required = true) Long id){
        return ResponseEntity.ok(insightService.deleteInsight(id));
    }

    @PostMapping("/uploadContentImage")
    public ResponseEntity<?> uploadContentImage(){
        return null;
    }
}
