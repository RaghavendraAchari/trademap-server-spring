package com.raghav.trademap.controller;

import com.raghav.trademap.requestResponseModel.TradeDetailsRequest;
import com.raghav.trademap.requestResponseModel.TradeDetailsResponse;
import com.raghav.trademap.service.TradeDetailsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tradedetails")
public class TradeDetailsController {

    @Autowired
    private TradeDetailsService tradeDetailsService;

    @GetMapping()
    public ResponseEntity<List<TradeDetailsResponse>> getTradeDetails(){
        return ResponseEntity.ok(tradeDetailsService.getTradeDetails());
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

    @GetMapping(value = "/getMaxDaysTraded", produces="application/json")
    public ResponseEntity<String> getMaxDayTraded(){
        JSONObject data = new JSONObject();
        data.put("days", tradeDetailsService.getMaxDayTraded());

        return ResponseEntity.ok(data.toString());
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public void postTradeDetails(@RequestPart("tradeDetails") TradeDetailsRequest tradeDetailsRequest, @RequestPart("images") MultipartFile[] images){
        tradeDetailsService.postTradeDetails(tradeDetailsRequest, images);

    }
}
