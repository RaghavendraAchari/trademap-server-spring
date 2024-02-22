package com.raghav.trademap.modules.tradeDetails;

import com.raghav.trademap.modules.tradeDetails.dto.*;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tradedetails")
public class TradeDetailsController {

    @Autowired
    private TradeDetailsService tradeDetailsService;

    @GetMapping()
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetails(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        List<TradeDetails> tradeDetails = tradeDetailsService.getTradeDetails(page, size);

        List<TradeDetailsResponse> response = tradeDetails.stream()
                                                .map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/setupsAndInstuments")
    public ResponseEntity<DistinctData> getDistinctData(){
        return ResponseEntity.ok(tradeDetailsService.getDistinctData());
    }

    @Deprecated
    @GetMapping("/lastFilledDate")
    public ResponseEntity<String> getLastFilledDate(){
        return ResponseEntity.ok(tradeDetailsService.getLastFilledDate());
    }

    @GetMapping("/pendingDates")
    public ResponseEntity<List<String>> getPendingDates(){
        return ResponseEntity.ok(tradeDetailsService.getPendingDates());
    }

    @GetMapping("/today")
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetailsOfToday(){
        List<TradeDetails> currentDayTrades = tradeDetailsService.getCurrentDayTrades();

        List<TradeDetailsResponse> response = currentDayTrades.stream()
                .map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/forDate")
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetailsForDay(@RequestParam("date") LocalDate date){
        List<TradeDetails> tradesForTheDate = tradeDetailsService.getTradesForTheDate(date);

        List<TradeDetailsResponse> responseList = tradesForTheDate.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/setNoTradingDay")
    public ResponseEntity<Boolean> setNoTradingDay(@RequestBody NoTradeRequest request){
        return ResponseEntity.ok(tradeDetailsService.setNoTradingDay(request));
    }

    @GetMapping(value = "/getMaxDaysTraded", produces="application/json")
    public ResponseEntity<MaxTradedDays> getMaxDayTraded(){
        Integer maxDayTraded = tradeDetailsService.getMaxDayTraded();

        return ResponseEntity.ok(new MaxTradedDays(maxDayTraded));
    }

    @GetMapping(value = "/downloadImage")
    public ResponseEntity<?> downloadImage(@RequestParam("path") String path){
        Path filePath = Paths.get(path);

        if(Files.exists(filePath)){
            try {
                Resource resource = new UrlResource(filePath.toUri());
                String fileType = Files.probeContentType(filePath);

                if(fileType == null)
                    fileType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(fileType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }catch (IOException e){
                throw new RuntimeException("Not able to read file");
            }
        }else {
            return ResponseEntity.internalServerError().body("File does not exist");
        }
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public ResponseEntity<TradeDetailsResponse> postTradeDetails(@RequestPart("tradeDetails") @Valid TradeDetailsRequest tradeDetailsRequest, @RequestPart("images") MultipartFile[] images){
        TradeDetails saved = tradeDetailsService.postTradeDetails(tradeDetailsRequest, images);

        return ResponseEntity.ok(TradeDetailsResponse.mapToTradeDetailsResponse(saved));

    }
}
