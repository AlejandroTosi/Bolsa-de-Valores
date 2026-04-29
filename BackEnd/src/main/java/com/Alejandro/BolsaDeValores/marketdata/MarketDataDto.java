package com.Alejandro.BolsaDeValores.marketdata;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MarketDataDto {
    public record MarketDataResponseDto(
            String Ticker,
            BigDecimal CurrentPrice,
            BigDecimal PreviousClose,
            BigDecimal DailyChange,
            BigDecimal DailyChangePercent,
            BigDecimal High,
            BigDecimal Low,
            BigDecimal Volume,
            LocalDateTime LastUpdated,
            String Source
    ){}

    public record HistoricalDataResponseDto(
            String ticker,
            java.util.List<MarketDataResponseDto> history
    ){}
}

