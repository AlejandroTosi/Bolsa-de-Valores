package com.Alejandro.BolsaDeValores.marketdata;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "market_data")
public class MarketDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Ticker cannot be blank")
    @NotNull(message = "Ticker cannot be null")
    private String Ticker;

    @Column(nullable = false)
    @NotNull(message = "Current price cannot be null")
    private BigDecimal CurrentPrice;

    @Column(nullable = false)
    @NotNull(message = "Previous close cannot be null")
    private BigDecimal PreviousClose;

    @Column(nullable = false)
    @NotNull(message = "Daily change percentage cannot be null")
    private BigDecimal DailyChangePercent;

    @Column(nullable = false)
    @NotNull(message = "Daily change cannot be null")
    private BigDecimal DailyChange;

    @Column
    private BigDecimal High;

    @Column
    private BigDecimal Low;

    @Column
    private BigDecimal Volume;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime CreatedAt;

    @Column(nullable = false)
    private LocalDateTime LastUpdated;

    @Column(nullable = false)
    private String Source = "ALPHA_VANTAGE";
}
