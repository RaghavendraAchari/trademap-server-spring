package com.raghav.trademap.modules.insights.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ContentImageUploadResponse {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileResponse{
        private String url;
    }

    private int success;
    private FileResponse file;
}
