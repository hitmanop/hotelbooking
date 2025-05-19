package com.springboot.hotelbooking.strategy;

import java.math.BigDecimal;

import com.springboot.hotelbooking.entity.Inventory;

public class BasePricingStrategy implements PricingStrategy{
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
