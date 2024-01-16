package com.raghav.trademap.modules.tradeDetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistinctData {
    private List<String> setupNames;
    private List<String> instrumentNames;
}
