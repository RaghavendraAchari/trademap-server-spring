package com.raghav.trademap.modules.investment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "investment_details")
@Data
public class InvestmentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String instrument;

    @Column(name = "investment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate investmentDate;

    @Column(name = "investment_amount")
    Long investedAmount;
}
