package com.raghav.trademap.modules.analytics;

import com.raghav.trademap.common.types.InstrumentType;
import com.raghav.trademap.modules.analytics.dto.Analytics;
import com.raghav.trademap.modules.investment.InvestmentDetailsRepo;
import com.raghav.trademap.modules.investment.model.InvestmentDetails;
import com.raghav.trademap.modules.settings.Settings;
import com.raghav.trademap.modules.settings.SettingsRepo;
import com.raghav.trademap.modules.tradeDetails.TradeDetailsRepo;
import com.raghav.trademap.modules.tradeDetails.dataClasses.DateAndProfit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class AnalyticsService {
    @Autowired
    InvestmentDetailsRepo investmentDetailsRepo;

    @Autowired
    TradeDetailsRepo tradeDetailsRepo;

    @Autowired
    SettingsRepo settingsRepo;


    //TODO - add insights details - user should be greeted with the learnings the he has done in his trading journey
    public Analytics getDetails(String userId){
        //TODO - add all the analytics data by fetching it with all the available repositories
        /*
        *
        * add total days (get start date- trackingDateDetails or settings) - then get cur date and date difference from
        * Calender
        */

        // investment details
        List<InvestmentDetails> all = investmentDetailsRepo.findAll();

        Long totalInvestedAmount = 0L;

        for(InvestmentDetails investmentDetails : all){
            totalInvestedAmount += investmentDetails.getInvestedAmount();
        }

        Integer totalTrades = tradeDetailsRepo.getTotalTrades();
        Integer lossTrades = tradeDetailsRepo.getLossTrades();
        Integer profitableTrades = tradeDetailsRepo.getProfitableTrades();


        Integer totalNoTradingDays = tradeDetailsRepo.getTotalNoTradingDays();
        Integer totalWeekends = tradeDetailsRepo.getTotalWeekends();
        Integer totalHolidays = tradeDetailsRepo.getTotalHolidays();
        Integer totalTradedDays = tradeDetailsRepo.getTotalTradedDays();

        Double totalPnl = tradeDetailsRepo.getTotalPnl();

        Integer totalTradesWithStocks = tradeDetailsRepo.getTotalTradesWithInstrumentType(InstrumentType.STOCK);
        Integer totalTradesWithIndex = tradeDetailsRepo.getTotalTradesWithInstrumentType(InstrumentType.INDEX);
        Integer totalTradesWithCommodity = tradeDetailsRepo.getTotalTradesWithInstrumentType(InstrumentType.COMMODITY);

        List<DateAndProfit> dateWiseProfit = tradeDetailsRepo.getDateWiseProfit();
        DateAndProfit maxLossDay = dateWiseProfit.stream().min(Comparator.comparing(DateAndProfit::getPnl)).orElse(null);
        DateAndProfit maxProfitDay = dateWiseProfit.stream().max(Comparator.comparing(DateAndProfit::getPnl)).orElse(null);

        Integer profitMakingdays = Long.valueOf(dateWiseProfit.stream().filter(it -> it.getPnl() >= 0).count()).intValue();
        Integer lossMakingdays = Long.valueOf(dateWiseProfit.stream().filter(it -> it.getPnl() < 0).count()).intValue();

        Settings settings = settingsRepo.findById(1).orElse(null);

        return Analytics.builder()
                .totalInvestment(totalInvestedAmount.doubleValue())
                .totalDays(settings != null ? Long.valueOf(DAYS.between(settings.getStartDate(), LocalDate.now())).intValue() : null)
                .totalWeekends(totalWeekends)
                .totalNoTradingDays(totalNoTradingDays)
                .totalHolidays(totalHolidays)
                .totalTradedDays(totalTradedDays)
                .totalTrades(totalTrades)
                .totalTradesInStock(totalTradesWithStocks)
                .totalTradesInIndex(totalTradesWithIndex)
                .totalTradesInCommodity(totalTradesWithCommodity)
                .totalPnl(totalPnl)
                .maxProfitInADay(maxProfitDay != null ? maxProfitDay.getPnl() : null)
                .dateOfMaxProfit(maxProfitDay != null ? maxProfitDay.getDate() : null)
                .maxLossInADay(maxLossDay != null ? maxLossDay.getPnl() : null)
                .dateOfMaxLoss(maxLossDay != null ? maxLossDay.getDate() : null)
                .noOfProfitTrades(profitableTrades)
                .noOfLossTrades(lossTrades)
                .dateWiseProfit(dateWiseProfit)
                .profitMakingDays(profitMakingdays)
                .lossMakingDays(lossMakingdays)
                .build();
    }
}
