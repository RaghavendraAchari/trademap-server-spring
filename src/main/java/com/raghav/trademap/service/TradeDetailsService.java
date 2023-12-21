package com.raghav.trademap.service;

import com.raghav.trademap.repo.TrackingDateDetailsRepo;
import com.raghav.trademap.requestResponseModel.PendingChartsAndTradesResponse;
import com.raghav.trademap.model.TradeDetails;
import com.raghav.trademap.repo.DailyChartImagesRepo;
import com.raghav.trademap.repo.TradeDetailsRepo;
import com.raghav.trademap.requestResponseModel.TradeDetailsRequest;
import com.raghav.trademap.requestResponseModel.TradeDetailsResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeDetailsService {
    @Autowired
    private TradeDetailsRepo tradeDetailsRepo;

    @Autowired
    private TrackingDateDetailsRepo trackingDateDetailsRepo;

    @Autowired
    private DailyChartImagesRepo dailyChartImagesRepo;

    public List<TradeDetailsResponse> getTradeDetails(){
        List<TradeDetails> tradeDetails = tradeDetailsRepo.findAll();
        List<TradeDetailsResponse> response = tradeDetails.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();

        return response;
    }

    public String getLastFilledDate(){
        LocalDateTime lastByDateTime = tradeDetailsRepo.findLastByDateTime();
        if(lastByDateTime != null)
            return lastByDateTime.toLocalDate().toString();

        else return LocalDate.now().toString();
    }

    public List<String> getPendingDates(){
        LocalDate trackingDate = trackingDateDetailsRepo.findById(1).get().getStartDate();
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
        String date = request.getDateTime().toLocalDate().toString();
        String setupName = request.getSetupName().trim().replace(" ", "-");

        List<String> paths = copyFileToSystem(request, images, setupName, date);

        TradeDetails tradeDetails = TradeDetailsRequest.mapToTradeDetails(request, paths);

        TradeDetails saved = tradeDetailsRepo.save(tradeDetails);

        return TradeDetailsResponse.mapToTradeDetailsResponse(saved);
    }

    private static List<String> copyFileToSystem(TradeDetailsRequest request, MultipartFile[] images, String setupName, String date) {
        List<String> paths = new ArrayList<>();

        for(int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];

            String fileType = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = "Trade-[" + setupName + "]-[" + request.getDateTime().toString().replace(":", "-").replace("T", " ") + "]-[" + (i+1) + "]." + fileType;

            Path path = Paths.get("User Data", date, "Trade Chart");

            if(!Files.exists(path)){
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try(InputStream inputStream = image.getInputStream()){
                Path filePath = path.resolve(fileName);
                Files.copy( inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                paths.add(filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return paths;
    }

    public Integer getMaxDayTraded(){
        Integer maxDay = tradeDetailsRepo.findMaxDay();
        return maxDay != null ? maxDay : 0;
    }

    public List<TradeDetailsResponse> getCurrentDayTrades() {
        List<TradeDetails> tradeDetailsOfCurrentDay = tradeDetailsRepo.findTradeDetailsOfCurrentDay();

        return tradeDetailsOfCurrentDay.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();
    }
}
