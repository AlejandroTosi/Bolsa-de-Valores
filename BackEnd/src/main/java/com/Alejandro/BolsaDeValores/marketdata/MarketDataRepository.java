package com.Alejandro.BolsaDeValores.marketdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarketDataRepository extends JpaRepository<MarketDataModel, Long> {

    Optional<MarketDataModel> findByTicker(String ticker);

    @Query("SELECT m FROM MarketDataModel m WHERE m.ticker = :ticker ORDER BY m.last_updated DESC LIMIT 1")
    Optional<MarketDataModel> findLatestByTicker(@Param("ticker") String ticker);

    @Query("SELECT m FROM MarketDataModel m WHERE m.ticker = :ticker AND m.last_updated >= :startTime ORDER BY m.last_updated DESC")
    List<MarketDataModel> findByTickerAndLastUpdatedAfter(
            @Param("ticker") String ticker,
            @Param("startTime") LocalDateTime startTime
    );

    @Query("SELECT m FROM MarketDataModel m WHERE m.last_updated >= :startTime ORDER BY m.last_updated DESC")
    List<MarketDataModel> findRecentData(@Param("startTime") LocalDateTime startTime);

    @Query("SELECT DISTINCT m.ticker FROM MarketDataModel m ORDER BY m.ticker ASC")
    List<String> findAllDistinctTickers();
}