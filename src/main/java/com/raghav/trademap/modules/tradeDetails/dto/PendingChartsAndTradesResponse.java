package com.raghav.trademap.modules.tradeDetails.dto;

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
