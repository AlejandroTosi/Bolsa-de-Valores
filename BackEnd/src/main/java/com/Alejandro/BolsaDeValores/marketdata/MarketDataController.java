package com.Alejandro.BolsaDeValores.marketdata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<MarketDataDto.MarketDataResponseDto> getLatestMarketData(
            @PathVariable String ticker) {

        return marketDataService.getLatestMarketData(ticker.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ticker}/historical")
    public ResponseEntity<MarketDataDto.HistoricalDataResponseDto> getHistoricalMarketData(
            @PathVariable String ticker,
            @RequestParam(defaultValue = "7") int days) {

        return marketDataService.getHistoricalMarketData(ticker.toUpperCase(), days)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{ticker}/refresh")
    public ResponseEntity<String> refreshMarketData(@PathVariable String ticker) {
        boolean updated = marketDataService.fetchAndSaveMarketData(ticker.toUpperCase());

        if (updated) {
            return ResponseEntity.ok("Market data refreshed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to refresh market data");
        }
    }
}