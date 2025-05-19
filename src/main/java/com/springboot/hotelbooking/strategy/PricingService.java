package com.springboot.hotelbooking.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.springboot.hotelbooking.entity.Inventory;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(Inventory inventory) {
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        // apply the additional strategies
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }

////    Return the sum of price of this inventory list
//    public BigDecimal calculateTotalPrice(List<Inventory> inventoryList) {
//        return inventoryList.stream()
//                .map(this::calculateDynamicPricing)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
}