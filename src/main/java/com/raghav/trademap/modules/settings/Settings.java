package com.raghav.trademap.modules.settings;

import com.raghav.trademap.modules.settings.dto.SettingsRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity
@Table(name = "settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "max_trade_limit")
    @ColumnDefault(value = "1")
    Integer maxTradeLimit;

    @Column(name = "warn_on_max_trade")
    @ColumnDefault(value = "true")
    Boolean warnOnMaxTrade;

    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @Column(name = "start_date")
    private LocalDate startDate;

}
