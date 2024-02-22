package com.raghav.trademap.modules.insights;

import com.raghav.trademap.common.utils.FileUtils;
import com.raghav.trademap.modules.insights.dto.*;
import com.raghav.trademap.modules.insights.model.Insight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/insights")
public class InsightController {

    @Autowired
    private InsightService insightService;

    @GetMapping()
    public ResponseEntity<List<InsightResponse>> getInsights(){
        List<Insight> allInsights = insightService.getAllInsights();

        return ResponseEntity.ok(allInsights.stream().map(InsightResponse::mapToInsightResponse).toList());
    }

    @GetMapping("/onlyTitles")
    public ResponseEntity<List<InsightsWithOnlyTitle>> getInsightsWithTitlesOnly(){
        return ResponseEntity.ok(insightService.getAllInsightsWithTitlesOnly());
    }

    @GetMapping("/:id")
    public ResponseEntity<InsightResponse> getInsightById(@PathVariable(name = "id") Long id){
        Insight insightById = insightService.getInsightById(id);

        return ResponseEntity.ok(InsightResponse.mapToInsightResponse(insightById));
    }

    @PostMapping()
    public ResponseEntity<InsightResponse> saveInsights(@RequestBody InsightRequest request){
        Insight insight = insightService.saveInsight(request);

        return ResponseEntity.ok(InsightResponse.mapToInsightResponse(insight));
    }

    @PutMapping()
    public ResponseEntity<InsightResponse> updateInsights(@RequestBody InsightUpdateRequest request){
        if(request.getId() == null)
            throw new RuntimeException("ID must not be null");

        Insight insight = insightService.updateInsight(request);

        return ResponseEntity.ok(InsightResponse.mapToInsightResponse(insight));
    }

    @DeleteMapping()
    public ResponseEntity<Boolean> deleteInsights(@RequestParam(required = true) Long id){
        return ResponseEntity.ok(insightService.deleteInsight(id));
    }

    @PostMapping("/uploadContentImage")
    public ResponseEntity<ContentImageUploadResponse> uploadContentImage(MultipartFile image){
        String urlPath = FileUtils.copyFileFromMultipartFile(image);

        String absolutePath = "http://localhost:8080/insights/downloadImage?path=" + UriUtils.encodePathSegment(urlPath, StandardCharsets.UTF_8);
        ContentImageUploadResponse response = ContentImageUploadResponse
                .builder()
                .success(!urlPath.isEmpty() ? 1 : 0)
                .file(new ContentImageUploadResponse.FileResponse(absolutePath))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/downloadImage")
    public ResponseEntity<?> downloadImage(@RequestParam("path") String path){
        Path filePath = Paths.get(path);

        log.info("Checking for file: " + path);

        if(Files.exists(filePath)){
            try {
                Resource resource = new UrlResource(filePath.toUri());
                String fileType = Files.probeContentType(filePath);

                if(fileType == null)
                    fileType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(fileType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }catch (IOException e){
                throw new RuntimeException("Not able to read file");
            }
        }else {
            return ResponseEntity.internalServerError().body("File does not exist");
        }
    }
}
