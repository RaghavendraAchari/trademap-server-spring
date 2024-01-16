package com.raghav.trademap.modules.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistinctData {
    private List<String> categories;
    private List<String> tags;
}
