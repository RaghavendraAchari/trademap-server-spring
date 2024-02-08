package com.raghav.trademap.modules.tradeDetails;

import com.raghav.trademap.common.TrackingDateDetails;
import com.raghav.trademap.common.TrackingDateDetailsRepo;
import com.raghav.trademap.modules.dailyChart.DailyChartImagesRepo;
import com.raghav.trademap.common.utils.FileUtils;
import com.raghav.trademap.modules.tradeDetails.dto.DistinctData;
import com.raghav.trademap.modules.tradeDetails.dto.NoTradeRequest;
import com.raghav.trademap.modules.tradeDetails.dto.TradeDetailsRequest;
import com.raghav.trademap.modules.tradeDetails.dto.TradeDetailsResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TradeDetailsService {
    @Autowired
    private TradeDetailsRepo tradeDetailsRepo;

    @Autowired
    private TrackingDateDetailsRepo trackingDateDetailsRepo;

    @Autowired
    private DailyChartImagesRepo dailyChartImagesRepo;

    public List<TradeDetailsResponse> getTradeDetails(Integer page, Integer size){
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dateTime"));

//        Page<TradeDetails> tradeDetails = tradeDetailsRepo.findAll(pageRequest);
        List<TradeDetails> tradeDetails = tradeDetailsRepo.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));

        return tradeDetails.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();
    }

    public String getLastFilledDate(){
        LocalDateTime lastByDateTime = tradeDetailsRepo.findLastByDateTime();

        if(lastByDateTime != null)
            return lastByDateTime.toLocalDate().toString();

        else return LocalDate.now().toString();
    }

    public List<String> getPendingDates(){
        Optional<TrackingDateDetails> result = trackingDateDetailsRepo.findById(1);

        if (result.isEmpty())
            throw new RuntimeException();

        LocalDate trackingDate = result.get().getStartDate();
        List<LocalDate> filledDates = tradeDetailsRepo.findDistinctDates(trackingDate);

        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.parse(trackingDate.toString());

        List<String> pendingDays = new ArrayList<>();

        while (startDate.isBefore(today) || startDate.isEqual(today)){
            if(!filledDates.contains(startDate)){
                pendingDays.add(startDate.toString());
            }else {
                filledDates.remove(startDate);
            }

            startDate = startDate.plusDays(1);
        }

        return pendingDays;
    }

    @Transactional
    public TradeDetailsResponse postTradeDetails(TradeDetailsRequest request, MultipartFile[] images){
        List<TradeDetails> result = tradeDetailsRepo.getTradesForTheDate(request.getDateTime().toLocalDate());

        if(result.size() == 1 ){
            TradeDetails trade = result.get(0);

            if(trade.isNoTradingDay() || trade.isHoliday() || Boolean.TRUE.equals(trade.getIsWeekend()))
                throw new RuntimeException("The day is set as no trading day.");
        }

        String date = request.getDateTime().toLocalDate().toString();
        String setupName = request.getSetupName().trim().replace(" ", "-");

        List<String> paths = FileUtils.copyFileToSystem(request, images, setupName, date);

        TradeDetails tradeDetails = TradeDetailsRequest.mapToTradeDetails(request, paths);

        TradeDetails saved = tradeDetailsRepo.save(tradeDetails);

        return TradeDetailsResponse.mapToTradeDetailsResponse(saved);
    }



    public Integer getMaxDayTraded(){
        Integer maxDay = tradeDetailsRepo.findMaxDay();
        return maxDay != null ? maxDay : 0;
    }

    public List<TradeDetailsResponse> getCurrentDayTrades() {
        List<TradeDetails> tradeDetailsOfCurrentDay = tradeDetailsRepo.findTradeDetailsOfCurrentDay();

        return tradeDetailsOfCurrentDay.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();
    }

    @Transactional
    public boolean setNoTradingDay(NoTradeRequest request) {
        Integer maxDay = null;

        TradeDetails tradeDetails = TradeDetails.builder()
                .noTradingDay(request.getNoTradingDay())
                .isHoliday(request.getIsHoliday())
                .isWeekend(request.getIsWeekend())
                .remarks(request.getRemarks())
                .dateTime(request.getDateTime())
                .build();

        if(request.getNoTradingDay()) {
            maxDay = tradeDetailsRepo.findMaxDay();
            tradeDetails.setDay(maxDay + 1);
        }

        TradeDetails saved = tradeDetailsRepo.save(tradeDetails);

        return saved.getId() != null;
    }

    public List<TradeDetailsResponse> getTradesForTheDate(LocalDate date) {
        List<TradeDetails> result = tradeDetailsRepo.getTradesForTheDate(date);

        return result.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();
    }

    public DistinctData getDistinctData() {
        List<String> setups = tradeDetailsRepo.findDistinctSetupName();
        List<String> instruments = tradeDetailsRepo.findDistinctInstrumentName();

        return new DistinctData(setups, instruments);
    }
}
