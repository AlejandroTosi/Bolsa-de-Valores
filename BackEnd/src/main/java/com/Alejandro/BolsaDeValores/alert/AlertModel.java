package com.Alejandro.BolsaDeValores.alert;

import com.Alejandro.BolsaDeValores.user.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class AlertModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    @NotBlank(message = "Ticker cannot be blank")
    private String ticker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Alert condition type cannot be null")
    private AlertConditionType condition_type;

    @Column(nullable = false)
    @NotNull(message = "Target value cannot be null")
    private BigDecimal target_value;

    @Column(nullable = false)
    private Boolean active = true;

    @Column
    private LocalDateTime last_triggered_at;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;

    @Column
    private String notification_channel = "DISCORD";

    @Column
    private String discord_webhook_url;


    public Long getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public String getTicker() {
        return ticker;
    }

    public AlertConditionType getConditionType() {
        return condition_type;
    }

    public BigDecimal getTargetValue() {
        return target_value;
    }

    public Boolean getActive() { return active; }

    public LocalDateTime getLastTriggeredAt() {
        return last_triggered_at;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    public String getNotificationChannel() {
        return notification_channel;
    }

    public String getDiscordWebhookUrl() {
        return discord_webhook_url;
    }


    public void setUser(UserModel user) {
        this.user = user;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setConditionType(AlertConditionType condition_type) {
        this.condition_type = condition_type;
    }

    public void setTargetValue(BigDecimal target_value) {
        this.target_value = target_value;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setLastTriggeredAt(LocalDateTime last_triggered_at) {
        this.last_triggered_at = last_triggered_at;
    }

    public void setNotificationChannel(String notification_channel) {
        this.notification_channel = notification_channel;
    }

    public void setDiscordWebhookUrl(String discord_webhook_url) {
        this.discord_webhook_url = discord_webhook_url;
    }
}