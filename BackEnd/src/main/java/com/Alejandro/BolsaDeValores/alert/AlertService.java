package com.Alejandro.BolsaDeValores.alert;

import com.Alejandro.BolsaDeValores.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public AlertService(AlertRepository alertRepository, UserRepository userRepository) {
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
    }

    public List<AlertDto.ResponseAlerts> getAlertsByUser(Long userId) {
        return alertRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseAlerts)
                .toList();

    }

    public List<AlertDto.ResponseAlerts> getActiveAlertsByUser(Long userId) {
        return alertRepository.findActiveByUserId(userId)
                .stream()
                .map(this::mapToResponseAlerts)
                .toList();
    }

    public AlertDto.ResponseAlerts getAlertById(Long id) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(STR."Alert not found with id: \{id}"));
        return mapToResponseAlerts(alert);
    }


    public AlertDto.ResponseAlerts createAlert(AlertDto.CreateAlertDto dto) {

        var user = userRepository.findById(dto.userId())
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + dto.userId()));

        AlertModel alert = new AlertModel();

        alert.setUser(user);
        alert.setTicker(dto.ticker());
        alert.setConditionType(dto.condition_type());
        alert.setTargetValue(dto.target_value());
        alert.setActive(true);
        alert.setNotificationChannel(
                dto.notification_channel() != null
                        ? dto.notification_channel()
                        : "DISCORD"
        );
        alert.setDiscordWebhookUrl(dto.discord_webhook_url());

        AlertModel savedAlert = alertRepository.save(alert);

        return mapToResponseAlerts(savedAlert);
    }

    @Transactional
    public AlertDto.ResponseAlerts toggleAlert(Long id,AlertDto.ToggleAlertDto dto) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(STR."Alert not found with id: \{id}"));

        if (!alert.getUser().getId().equals(dto.user_id())) {
            throw new IllegalArgumentException("User not authorized to toggle this alert");
        }

        alert.setActive(dto.active());
        AlertModel updatedAlert = alertRepository.save(alert);
        return mapToResponseAlerts(updatedAlert);
    }

    @Transactional
    public void deleteAlert(Long id, Long userId) {
                AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(STR."Alert not found with id: \{id}"));

        if (!alert.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to delete this alert");
        }

        alertRepository.deleteById(id);
    }

    public List<String> getActiveAlertTickers() {
        return alertRepository.findAllActiveTickers();
    }

    private AlertDto.ResponseAlerts mapToResponseAlerts(AlertModel alert) {
        return new AlertDto.ResponseAlerts(
                alert.getId(),
                alert.getUser().getId(),
                alert.getTicker(),
                alert.getConditionType(),
                alert.getTargetValue(),
                alert.getActive(), // Fixed from .setActive()
                alert.getLastTriggeredAt(),
                alert.getNotificationChannel(),
                alert.getCreatedAt(),
                alert.getUpdatedAt()
        );
    }
    @Transactional
    public AlertDto.ResponseAlerts updateAlert(Long id, AlertDto.UpdateAlertDto dto) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(STR."Alert not found with id: \{id}"));

        alert.setTicker(dto.ticker());
        alert.setConditionType(dto.condition_type());
        alert.setTargetValue(dto.target_value());
        alert.setActive(dto.active());
        alert.setNotificationChannel(dto.notification_channel() != null ? dto.notification_channel() : "DISCORD");
        alert.setDiscordWebhookUrl(dto.discord_webhook_url());

        AlertModel updatedAlert = alertRepository.save(alert);
        return mapToResponseAlerts(updatedAlert);
    }
}
