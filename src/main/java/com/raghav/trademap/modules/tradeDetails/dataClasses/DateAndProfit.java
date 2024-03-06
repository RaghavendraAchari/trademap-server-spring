package com.raghav.trademap.modules.tradeDetails.dataClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DateAndProfit{
    @JsonFormat(pattern = "YYYY-MM-dd")
    LocalDate date;
    Double pnl;

    public DateAndProfit(LocalDate date, Double pnl){
        this.date = date;
        this.pnl = pnl;
    }
}
