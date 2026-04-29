package com.Alejandro.BolsaDeValores.alert;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AlertDto {

    public record ResponseAlerts(
            Long id,
            Long userId,
            String ticker,
            AlertConditionType conditionType,
            BigDecimal targetValue,
            Boolean active,
            LocalDateTime lastTriggeredAt,
            String notification_channel,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    public record CreateAlertDto(
            @NotNull(message = "User ID é obrigatório")
            Long userId,

            @NotBlank(message = "Ticker não pode estar vazio")
            String ticker,

            @NotNull(message = "Tipo de condição é obrigatório")
            AlertConditionType conditionType,

            @NotNull(message = "Valor alvo é obrigatório")
            @Positive(message = "O valor deve ser maior que zero")
            BigDecimal targetValue,

            String notification_channel,
            String discord_webhook_url
    ){}

    public record UpdateAlertDto(
            @NotBlank(message = "Ticker não pode estar vazio")
            String ticker,

            @NotNull(message = "Tipo de condição é obrigatório")
            AlertConditionType condition_type,

            @NotNull(message = "Valor alvo é obrigatório")
            @Positive(message = "O valor deve ser maior que zero")
            BigDecimal target_value,

            Boolean active,
            String notification_channel,
            String discord_webhook_url
    ){}

    public record ToggleAlertDto(
            @NotNull(message = "User ID é obrigatório")
            Long userId,

            @NotNull(message = "O status 'active' deve ser informado")
            Boolean active
    ){}

    public record GetAlertDto(
            @NotNull(message = "User ID é obrigatório")
            Long userId
    ){}
}