package com.springboot.hotelbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.hotelbooking.entity.Hotel;
import com.springboot.hotelbooking.entity.Inventory;
import com.springboot.hotelbooking.repository.HotelRepository;
import com.springboot.hotelbooking.repository.InventoryRepository;
import com.springboot.hotelbooking.strategy.PricingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {



    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final PricingService pricingService;

  // @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "0 0 * * * *")
    public void updatePrices() {
        int page = 0;
        int batchSize = 100;

        while(true) {
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page, batchSize));
            if(hotelPage.isEmpty()) {
                break;
            }
            hotelPage.getContent().forEach(this::updateHotelPrices);

            page++;
        }
    }

    private void updateHotelPrices(Hotel hotel) {
        log.info("Updating hotel prices for hotel ID: {}", hotel.getId());
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(1);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel, startDate, endDate);

        updateInventoryPrices(inventoryList);

    }



    private void updateInventoryPrices(List<Inventory> inventoryList) {
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }

}
