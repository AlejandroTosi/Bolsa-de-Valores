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

    public List<AlertDto.ResponseAlerts> getActiveAlertsForUser(AlertDto.GetAlertDto dto) {
        return alertRepository.findByUser_IdAndActiveTrue(dto.userId())
                .stream()
                .map(this::mapToResponseAlerts)
                .toList();
    }

    public List<AlertDto.ResponseAlerts> getAllAlertsByUser(AlertDto.GetAlertDto dto) {
        return alertRepository.findByUser_Id(dto.userId())
                .stream()
                .map(this::mapToResponseAlerts)
                .toList();
    }

    public AlertDto.ResponseAlerts getAlertById(Long id) {
        return alertRepository.findById(id)
                .map(this::mapToResponseAlerts)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado com id: " + id));
    }

    @Transactional
    public AlertDto.ResponseAlerts createAlert(AlertDto.CreateAlertDto dto) {
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        AlertModel alert = new AlertModel();
        alert.setUser(user);
        alert.setTicker(dto.ticker());
        alert.setConditionType(dto.conditionType());
        alert.setTargetValue(dto.targetValue());
        alert.setActive(true);
        alert.setNotificationChannel(dto.notification_channel() != null ? dto.notification_channel() : "DISCORD");
        alert.setDiscordWebhookUrl(dto.discord_webhook_url());

        return mapToResponseAlerts(alertRepository.save(alert));
    }

    @Transactional
    public AlertDto.ResponseAlerts toggleAlert(Long id, AlertDto.ToggleAlertDto dto) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));

        validateOwnership(alert, dto.userId());

        alert.setActive(dto.active());
        return mapToResponseAlerts(alert);
    }

    @Transactional
    public void deleteAlert(Long id, Long userId) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));

        validateOwnership(alert, userId);
        alertRepository.delete(alert);
    }

    @Transactional
    public AlertDto.ResponseAlerts updateAlert(Long id, AlertDto.UpdateAlertDto dto) {
        AlertModel alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));



        alert.setTicker(dto.ticker());
        alert.setConditionType(dto.condition_type());
        alert.setTargetValue(dto.target_value());
        alert.setActive(dto.active());
        alert.setNotificationChannel(dto.notification_channel() != null ? dto.notification_channel() : "DISCORD");
        alert.setDiscordWebhookUrl(dto.discord_webhook_url());

        return mapToResponseAlerts(alert);
    }


    private void validateOwnership(AlertModel alert, Long userId) {
        if (!alert.getUser().getId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a modificar este alerta");
        }
    }

    private AlertDto.ResponseAlerts mapToResponseAlerts(AlertModel alert) {
        return new AlertDto.ResponseAlerts(
                alert.getId(),
                alert.getUser().getId(),
                alert.getTicker(),
                alert.getConditionType(),
                alert.getTargetValue(),
                alert.getActive(),
                alert.getLastTriggeredAt(),
                alert.getNotificationChannel(),
                alert.getCreatedAt(),
                alert.getUpdatedAt()
        );
    }
}