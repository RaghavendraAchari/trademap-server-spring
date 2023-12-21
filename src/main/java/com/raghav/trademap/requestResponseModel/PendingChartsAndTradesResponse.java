package com.raghav.trademap.requestResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingChartsAndTradesResponse {
    private String lastUpdatedDateForChartsImages;
    private String lastUpdatedDateForTades;
}
