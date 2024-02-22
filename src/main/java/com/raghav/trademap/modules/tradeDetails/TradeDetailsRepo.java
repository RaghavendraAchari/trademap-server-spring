package com.raghav.trademap.modules.tradeDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeDetailsRepo extends JpaRepository<TradeDetails, Long> {

    @Query("select a.dateTime from TradeDetails a order by a.dateTime DESC limit 1")
    LocalDateTime findLastByDateTime();

    @Query("select MAX(a.day) from TradeDetails a")
    Integer findLastDayOfTrade();

    @Query("select a from TradeDetails a where Date(a.dateTime) = Date(CURRENT_DATE)")
    List<TradeDetails> findTradeDetailsOfCurrentDay();

    @Query("SELECT DISTINCT CAST(date(a.dateTime) AS LocalDate) FROM TradeDetails a WHERE date(a.dateTime) >= :trackingDate")
    List<LocalDate> findDistinctDates(@Param("trackingDate") LocalDate trackingDate);


    @Query("select MAX(a.day) from TradeDetails a where a.isHoliday != true and DATE(a.dateTime) != DATE(CURRENT_DATE)")
    Integer findMaxDay();

    @Query("select a from TradeDetails a where Date(a.dateTime) = :date")
    List<TradeDetails> getTradesForTheDate(@Param("date") LocalDate date);

    @Query("select distinct a.instrumentName from TradeDetails a where a.instrumentName is not null")
    List<String> findDistinctInstrumentName();

    @Query("select distinct a.setupName from TradeDetails a where a.setupName is not null")
    List<String> findDistinctSetupName();

    Page<TradeDetails> findAll(Specification<TradeDetails> specification, Pageable pageable);

    List<TradeDetails> findAll(Specification<TradeDetails> specification, Sort sort);
}
