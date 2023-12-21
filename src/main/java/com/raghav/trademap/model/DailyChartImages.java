package com.raghav.trademap.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "daily_chart_images")
@Data
public class DailyChartImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "index_type")
    private IndexType indexType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_holiday")
    private Boolean isHoliday;

    @Column(name = "is_weekend")
    private Boolean isWeekend;

    @Column(name = "chart_image_path")
    private String chartImagePath;
}
