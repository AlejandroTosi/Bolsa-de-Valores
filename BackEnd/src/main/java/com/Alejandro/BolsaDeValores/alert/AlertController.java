package com.Alejandro.BolsaDeValores.alert;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/alerts")

public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/active")
    public List<AlertDto.ResponseAlerts> getActiveAlertsForUser(@RequestParam Long userId) {
        return alertService.getAlertsByUser(userId);
    }

    @GetMapping("/{id}")
        public AlertDto.ResponseAlerts getAlertById(@PathVariable Long id){
        return alertService.getAlertById(id);
    }

    @PostMapping
    public ResponseEntity<AlertDto.ResponseAlerts> createAlert(
            @RequestBody AlertDto.CreateAlertDto dto) {
        AlertDto.ResponseAlerts alert = alertService.createAlert(dto);
        return ResponseEntity.ok(alert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAlert(@PathVariable Long id,@RequestBody AlertDto.UpdateAlertDto dto) {
        AlertDto.ResponseAlerts alerts = alertService.updateAlert(id, dto);
        return ResponseEntity.ok(alerts);
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<Object> toggleAlert(@PathVariable Long id, @RequestBody AlertDto.ToggleAlertDto dto) {
        AlertDto.ResponseAlerts updated = alertService.toggleAlert(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(
            @PathVariable Long id,
            @RequestParam Long userId) {

        alertService.deleteAlert(id, userId);
        return ResponseEntity.ok().build();
    }
}

    // ToDO: Admin endpoints for managing all alerts (get all, get by id, update, delete)

