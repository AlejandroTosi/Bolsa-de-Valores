package com.Alejandro.BolsaDeValores.marketdata;

import com.Alejandro.BolsaDeValores.alert.AlertService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class MarketDataService {

    private final ConectionApi conectionApi;
    private final MarketDataRepository repository;
    private final AlertService alertService;



    public MarketDataService(ConectionApi conectionApi,
                             MarketDataRepository repository,
                             AlertService alertService) {

        this.conectionApi = conectionApi;
        this.repository = repository;
        this.alertService = alertService;
    }
    public Optional<MarketDataDto.MarketDataResponseDto> getLatestMarketData(String ticker) {
        return repository.findLatestByTicker(ticker)
                .map(this::mapToDto);
    }

    public Optional<MarketDataDto.HistoricalDataResponseDto> getHistoricalMarketData(String ticker, int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<MarketDataModel> history = repository.findByTickerAndLastUpdatedAfter(ticker, startDate);

        if (history.isEmpty()) {
            return Optional.empty();
        }

        List<MarketDataDto.MarketDataResponseDto> dailyData = history.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return Optional.of(new MarketDataDto.HistoricalDataResponseDto(ticker, dailyData));
    }

    public boolean fetchAndSaveMarketData(String ticker) {
        try {
            MarketDataModel saved = fetchAndSave(ticker);
            return saved != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void processMarketDataAndTriggerAlerts() {
        List<String> activeTickers = alertService.getActiveAlertsForUser();
        List<MarketDataModel> updatedData = activeTickers.stream()
                .map(this::fetchAndSave)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private MarketDataModel fetchAndSave(String ticker) {
        MarketDataDto.MarketDataResponseDto dto = conectionApi.fetchMarketData(ticker);

        if (dto == null) return null;

        MarketDataModel entity = repository.findByTicker(ticker)
                .orElse(new MarketDataModel());
        entity.setTicker(dto.Ticker());
        entity.setCurrentPrice(dto.CurrentPrice());
        entity.setPreviousClose(dto.PreviousClose());
        entity.setDailyChange(dto.DailyChange());
        entity.setDailyChangePercent(dto.DailyChangePercent());
        entity.setHigh(dto.High());
        entity.setLow(dto.Low());
        entity.setVolume(dto.Volume());
        entity.setLastUpdated(LocalDateTime.now());
        entity.setSource(dto.Source());

        return repository.save(entity);

   }

    private MarketDataDto.MarketDataResponseDto mapToDto(MarketDataModel model) {
                return new MarketDataDto.MarketDataResponseDto(
                model.getTicker(),
                model.getCurrentPrice(),
                model.getPreviousClose(),
                model.getDailyChange(),
                model.getDailyChangePercent(),
                model.getHigh(),
                model.getLow(),
                model.getVolume(),
                model.getLastUpdated(),
                model.getSource()
        );

    }

}