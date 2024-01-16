package com.raghav.trademap.modules.investment;

import com.raghav.trademap.modules.investment.model.InvestmentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/investmentdetails")
public class InvestmentController {
    @Autowired
    InvestmentDetailsRepo investmentDetailsRepo;

    @GetMapping
    public ResponseEntity<List<InvestmentDetails>> getInvestmentDetails(){
        return ResponseEntity.ok(investmentDetailsRepo.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }
}
