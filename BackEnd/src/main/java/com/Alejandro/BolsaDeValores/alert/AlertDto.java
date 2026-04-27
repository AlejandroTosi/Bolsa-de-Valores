package com.Alejandro.BolsaDeValores.alert;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AlertDto {

    public record ResponseAlerts(
            Long id,
            Long user_id,
            String ticker,
            AlertConditionType condition_type,
            BigDecimal target_value,
            Boolean active,
            LocalDateTime last_triggered_at,
            String notification_channel,
            LocalDateTime created_at,
            LocalDateTime updated_at
    ){}

    public record CreateAlertDto(

            @NotNull(message = "User ID cannot be blank")
            Long userId,
            @NotBlank(message = "Ticker cannot be blank")
            String ticker,
            
            @NotNull(message = "Condition type cannot be null")
            AlertConditionType condition_type,
            
            @NotNull(message = "Target value cannot be null")
            @Positive(message = "Target value must be positive")
            BigDecimal target_value,
            
            String notification_channel,
            String discord_webhook_url
    ){}

    public record UpdateAlertDto(
            @NotBlank(message = "Ticker cannot be blank")
            String ticker,
            
            @NotNull(message = "Condition type cannot be null")
            AlertConditionType condition_type,
            
            @NotNull(message = "Target value cannot be null")
            @Positive(message = "Target value must be positive")
            BigDecimal target_value,
            
            Boolean active,
            String notification_channel,
            String discord_webhook_url
    ){}

    public record ToggleAlertDto(
            Long user_id,
            Boolean active
    ){}

}
