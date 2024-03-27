package com.raghav.trademap.modules.tradeDetails;

import com.raghav.trademap.modules.dailyChart.DailyChartImagesRepo;
import com.raghav.trademap.common.utils.FileUtils;
import com.raghav.trademap.modules.settings.Settings;
import com.raghav.trademap.modules.settings.SettingsRepo;
import com.raghav.trademap.modules.tradeDetails.dto.DistinctData;
import com.raghav.trademap.modules.tradeDetails.dto.NoTradeRequest;
import com.raghav.trademap.modules.tradeDetails.dto.TradeDetailsRequest;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class TradeDetailsService {
    @Autowired
    private TradeDetailsRepo tradeDetailsRepo;

    @Autowired
    private SettingsRepo settingsRepo;

    @Autowired
    private DailyChartImagesRepo dailyChartImagesRepo;

    public List<TradeDetails> getTradeDetails(Integer page, Integer size, Sort.Direction sortDirection,
                                              Boolean showHoliday, Boolean showWeekend,
                                              Boolean showNoTradingDay, String user){
        log.info("Getting all trades from DB with Sort:" + sortDirection);

        Specification<TradeDetails> specification = new Specification<>() {
            @Override
            public Predicate toPredicate(Root<TradeDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(!showHoliday) predicates.add(criteriaBuilder.equal(root.get("isHoliday"), false));
                if(!showWeekend) predicates.add(criteriaBuilder.equal(root.get("isWeekend"), false));
                if(!showNoTradingDay) predicates.add(criteriaBuilder.equal(root.get("noTradingDay"), false));

                predicates.add(criteriaBuilder.equal(root.get("userId"), user));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };

        if(page == null || size == null){
            return tradeDetailsRepo.findAll(specification, Sort.by(sortDirection, "dateTime"));
        }

        Pageable pageData = PageRequest.of(page, size, Sort.by(sortDirection, "dateTime"));
        return tradeDetailsRepo.findAll(specification, pageData).getContent();
    }

    public List<String> getPendingDates(String user){
        Optional<Settings> result = settingsRepo.findByUserId(user);

        if (result.isEmpty())
            throw new RuntimeException();

        LocalDate trackingDate = result.get().getStartDate();
        List<LocalDate> filledDates = tradeDetailsRepo.findDistinctDates(trackingDate, user);

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
    public TradeDetails postTradeDetails(TradeDetailsRequest request,
                                         MultipartFile[] images,
                                         String user){
        List<TradeDetails> result = tradeDetailsRepo.getTradesForTheDate(user, request.getDateTime().toLocalDate());

        //if already set as 'no trading day' then throw an error
        if(result.size() == 1 ){
            TradeDetails trade = result.get(0);

            if(trade.isNoTradingDay() || trade.isHoliday() || Boolean.TRUE.equals(trade.getIsWeekend()))
                throw new RuntimeException("The day is set as no trading day.");
        }

        //copy the file and get path
        String date = request.getDateTime().toLocalDate().toString();
        String setupName = request.getSetupName().trim().replace(" ", "-");

        List<String> paths = FileUtils.copyFileToSystem(request, images, setupName, date);

        //create db entity with image paths
        TradeDetails tradeDetails = TradeDetailsRequest.mapToTradeDetails(request, paths, user);

        return tradeDetailsRepo.save(tradeDetails);
    }



    public Integer getMaxDayTraded(String user){
        Integer maxDay = tradeDetailsRepo.findMaxDay(user);
        return maxDay != null ? maxDay : 0;
    }

//    public List<TradeDetails> getCurrentDayTrades() {
//        return tradeDetailsRepo.findTradeDetailsOfCurrentDay();
//    }

    @Transactional
    public boolean setNoTradingDay(NoTradeRequest request, String user) {
        Integer maxDay = null;

        TradeDetails tradeDetails = TradeDetails.builder()
                .noTradingDay(request.getNoTradingDay())
                .isHoliday(request.getIsHoliday())
                .isWeekend(request.getIsWeekend())
                .remarks(request.getRemarks())
                .dateTime(request.getDateTime())
                .userId(user)
                .build();

        if(request.getNoTradingDay()) {
            maxDay = tradeDetailsRepo.findMaxDay(user);
            tradeDetails.setDay(maxDay + 1);
        }

        TradeDetails saved = tradeDetailsRepo.save(tradeDetails);

        return saved.getId() != null;
    }

    public List<TradeDetails> getTradesForTheDate(String user, LocalDate date) {
        return tradeDetailsRepo.getTradesForTheDate(user, date);
    }

    public DistinctData getDistinctData(String user) {
        List<String> setups = tradeDetailsRepo.findDistinctSetupName(user);
        List<String> instruments = tradeDetailsRepo.findDistinctInstrumentName();

        return new DistinctData(setups, instruments);
    }
}
