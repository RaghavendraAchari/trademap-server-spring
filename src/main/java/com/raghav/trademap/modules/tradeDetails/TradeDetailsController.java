package com.raghav.trademap.modules.tradeDetails;

import com.raghav.trademap.modules.tradeDetails.dto.*;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tradedetails")
public class TradeDetailsController {

    @Autowired
    private TradeDetailsService tradeDetailsService;

    @GetMapping()
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetails(Principal principal,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size,
                                                                      @RequestParam(defaultValue = "DESC") Sort.Direction sort,
                                                                      @RequestParam(defaultValue = "true") Boolean showHoliday,
                                                                      @RequestParam(defaultValue = "true") Boolean showNoTradingDay,
                                                                      @RequestParam(defaultValue = "true") Boolean showWeekend ){
        List<TradeDetails> tradeDetails = tradeDetailsService.getTradeDetails(page, size, sort, showHoliday, showWeekend, showNoTradingDay, principal.getName());

        List<TradeDetailsResponse> response = tradeDetails.stream()
                                                .map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/setupsAndInstuments")
    public ResponseEntity<DistinctData> getDistinctData(Principal principal){
        return ResponseEntity.ok(tradeDetailsService.getDistinctData(principal.getName()));
    }


    @GetMapping("/pendingDates")
    public ResponseEntity<List<String>> getPendingDates(Principal principal){
        return ResponseEntity.ok(tradeDetailsService.getPendingDates(principal.getName()));
    }

//    @GetMapping("/today")
//    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetailsOfToday(Principal principal){
//        List<TradeDetails> currentDayTrades = tradeDetailsService.getCurrentDayTrades();
//
//        List<TradeDetailsResponse> response = currentDayTrades.stream()
//                .map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/forDate")
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetailsForDay(Principal principal, @RequestParam("date") LocalDate date){
        List<TradeDetails> tradesForTheDate = tradeDetailsService.getTradesForTheDate(principal.getName(), date);

        List<TradeDetailsResponse> responseList = tradesForTheDate.stream().map(TradeDetailsResponse::mapToTradeDetailsResponse).toList();

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/setNoTradingDay")
    public ResponseEntity<Boolean> setNoTradingDay(Principal principal, @RequestBody NoTradeRequest request){
        return ResponseEntity.ok(tradeDetailsService.setNoTradingDay(request, principal.getName()));
    }

    @GetMapping(value = "/getMaxDaysTraded", produces="application/json")
    public ResponseEntity<MaxTradedDays> getMaxDayTraded(Principal principal){
        Integer maxDayTraded = tradeDetailsService.getMaxDayTraded(principal.getName());

        return ResponseEntity.ok(new MaxTradedDays(maxDayTraded));
    }

    @GetMapping(value = "/downloadImage")
    public ResponseEntity<?> downloadImage(Principal principal, @RequestParam("path") String path){
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
    public ResponseEntity<TradeDetailsResponse> postTradeDetails(Principal principal,
                                                                 @RequestPart("tradeDetails") @Valid TradeDetailsRequest tradeDetailsRequest,
                                                                 @RequestPart("images") MultipartFile[] images){
        TradeDetails saved = tradeDetailsService.postTradeDetails(tradeDetailsRequest, images, principal.getName());

        return ResponseEntity.ok(TradeDetailsResponse.mapToTradeDetailsResponse(saved));

    }
}
