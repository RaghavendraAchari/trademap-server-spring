package com.raghav.trademap.repo;

import com.raghav.trademap.model.TradeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TradeDetailsRepo extends JpaRepository<TradeDetails, Long> {

    @Query("select a.dateTime from TradeDetails a order by a.dateTime DESC limit 1")
    LocalDateTime findLastByDateTime();

    @Query("select MAX(a.day) from TradeDetails a")
    Integer findLastDayOfTrade();

    @Query("select a from TradeDetails a where a.dateTime = CURRENT_DATE")
    List<TradeDetails> findTradeDetailsOfCurrentDay();

    @Query("select DISTINCT(DATE(a.dateTime)) from TradeDetails a where DATE(a.dateTime) >= :trackingDate")
    List<LocalDate> findDistinctDates(LocalDate trackingDate);

    @Query("select MAX(a.day) from TradeDetails a where a.isHoliday != true and DATE(a.dateTime) != DATE(CURRENT_DATE)")
    Integer findMaxDay();
}
