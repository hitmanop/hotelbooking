package com.springboot.hotelbooking.strategy;

import java.math.BigDecimal;

import com.springboot.hotelbooking.entity.Inventory;

public interface PricingStrategy {
	
	BigDecimal calculatePrice(Inventory inventory);

}
