package com.raghav.trademap.modules.tradeDetails;

import com.raghav.trademap.modules.tradeDetails.dto.DistinctData;
import com.raghav.trademap.modules.tradeDetails.dto.NoTradeRequest;
import com.raghav.trademap.modules.tradeDetails.dto.TradeDetailsRequest;
import com.raghav.trademap.modules.tradeDetails.dto.TradeDetailsResponse;
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
        return ResponseEntity.ok(tradeDetailsService.getTradeDetails(page, size));
    }

    @GetMapping("/setupsAndInstuments")
    public ResponseEntity<DistinctData> getDistinctData(){
        return ResponseEntity.ok(tradeDetailsService.getDistinctData());
    }

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
        return ResponseEntity.ok(tradeDetailsService.getCurrentDayTrades());
    }

    @GetMapping("/forDate")
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetailsForDay(@RequestParam("date") LocalDate date){
        return ResponseEntity.ok(tradeDetailsService.getTradesForTheDate(date));
    }

    @PostMapping("/setNoTradingDay")
    public ResponseEntity<Boolean> setNoTradingDay(@RequestBody NoTradeRequest request){
        return ResponseEntity.ok(tradeDetailsService.setNoTradingDay(request));
    }

    @GetMapping(value = "/getMaxDaysTraded", produces="application/json")
    public ResponseEntity<String> getMaxDayTraded(){
        JSONObject data = new JSONObject();
        data.put("days", tradeDetailsService.getMaxDayTraded());

        return ResponseEntity.ok(data.toString());
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
        TradeDetailsResponse tradeDetailsResponse = tradeDetailsService.postTradeDetails(tradeDetailsRequest, images);

        return ResponseEntity.ok(tradeDetailsResponse);

    }


}
