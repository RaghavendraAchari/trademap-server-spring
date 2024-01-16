package com.raghav.trademap.common;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name = "tracking_date_details")
@Data
public class TrackingDateDetails {
    @Id
    Integer id;

    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @Column(name = "start_date")
    private LocalDate startDate;
}
