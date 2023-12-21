package com.raghav.trademap.model;

import com.raghav.trademap.requestResponseModel.TradeDetailsRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trade_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private Integer day;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "no_trading_day")
    private boolean noTradingDay;

    @Column(name = "is_holiday")
    private boolean isHoliday;

    @Enumerated(EnumType.STRING)
    @Column(name = "instrument_type")
    private InstrumentType instrumentType;

    @Column(name = "instrument_name")
    private String instrumentName;

    @Column(name = "setup_name")
    private String setupName;

    @Column(name = "remarks", length = 8000)
    private String remarks;

    @Column(name = "risk_to_reward")
    private float riskToReward;

    @Column(name = "risk_to_reward_on_premium")
    private float riskToRewardOnPremium;

    @Column(name = "result_type")
    private String resultType;

    @Column(name = "pnl")
    private double pnl;

    @ElementCollection()
    @Column(name = "image_paths")
    private List<String> imagePaths;

}
