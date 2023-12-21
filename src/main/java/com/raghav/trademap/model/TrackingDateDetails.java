package com.raghav.trademap.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tracking_date_details")
@Data
public class TrackingDateDetails {
    @Id
    Integer id;

    @Column(name = "start_date")
    private LocalDate startDate;
}
