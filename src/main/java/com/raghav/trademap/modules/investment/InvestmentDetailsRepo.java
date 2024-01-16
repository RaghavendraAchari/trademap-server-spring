package com.raghav.trademap.modules.investment;

import com.raghav.trademap.modules.investment.model.InvestmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentDetailsRepo extends JpaRepository<InvestmentDetails, Integer> {
}
