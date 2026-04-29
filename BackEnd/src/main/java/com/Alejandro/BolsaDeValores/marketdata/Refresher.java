/*

This refresher is disabled by default for develpoment
and because of API free tier limits.




package com.Alejandro.BolsaDeValores.marketdata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Refresher {

    private final MarketDataService marketDataService;

    @Value("${scheduler.market-data-fetch.enabled:true}")
    private boolean enabled;

    public MarketDataScheduler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Scheduled(fixedRateString = "${scheduler.market-data-fetch.fixed-rate:60000}")
    public void fetchMarketDataAndProcessAlerts() {
        if (enabled) {
            marketDataService.processMarketDataAndTriggerAlerts();
        }
    }

    @Scheduled(fixedRate = 300000)
    public void healthCheck() {
        if (enabled) {
            System.out.println("Scheduler is running...");
        }
    }
*/