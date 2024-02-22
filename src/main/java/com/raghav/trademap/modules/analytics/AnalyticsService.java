package com.raghav.trademap.modules.analytics;

import com.raghav.trademap.modules.analytics.dto.AnalyticsResponse;
import com.raghav.trademap.modules.insights.InsightsRepo;
import com.raghav.trademap.modules.investment.InvestmentDetailsRepo;
import com.raghav.trademap.modules.investment.model.InvestmentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class AnalyticsService {
    @Autowired
    InvestmentDetailsRepo investmentDetailsRepo;


    //TODO - add insights details - user should be greeted with the learnings the he has done in his trading journey
    public AnalyticsResponse getDetails(String userId){
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

        /*
        * get total weekends and total no trading days then total holidays - create a POJO and get it from db
        *
        * @Query("SELECT new com.raghav.trademap.modules.analytics.model.DaysAggregation(COUNT(c.weekend), COUNT(c.noTradingDay), COUNT(c.holiday)) "
                  + "FROM TradeDetails AS c ")
                List<CommentCount> countTotalCommentsByYearClass();
        *
        * get aggregation of stocks, index, commodity segments
        *
        * get pnl statements
        *
        * get max pnl with date
        *
        * return data
        */
        return AnalyticsResponse.builder()
                .totalInvestment(130000.0)
                .totalDays(100)
                .totalWeekends(20)
                .totalNoTradingDays(18)
                .totalHolidays(16)
                .totalTrades(155)
                .totalTradesInStock(20)
                .totalTradesInIndex(110)
                .totalTradesInCommodity(25)
                .totalPnl(12000.0)
                .maxProfitInADay(1200.0)
                .dateOfMaxProfit(LocalDate.of(2023, Month.DECEMBER, 20))
                .maxLossInADay(-1020.0)
                .dateOfMaxLoss(LocalDate.of(2023, Month.JANUARY, 10))
                .noOfProfitTrades(50)
                .noOfLossTrades(20)
                .build();
    }
}
