package com.Alejandro.BolsaDeValores.alert;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<AlertDto.ResponseAlerts>> getActiveAlertsForUser(AlertDto.GetAlertDto dto) {
        return ResponseEntity.ok(alertService.getActiveAlertsForUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertDto.ResponseAlerts> getAlertById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.getAlertById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlertDto.ResponseAlerts>> getAllAlertsForUser(AlertDto.GetAlertDto dto) {
        return ResponseEntity.ok(alertService.getAllAlertsByUser(dto));
    }

    @PostMapping
    public ResponseEntity<AlertDto.ResponseAlerts> createAlert(@Valid @RequestBody AlertDto.CreateAlertDto dto) {
        AlertDto.ResponseAlerts alert = alertService.createAlert(dto);
        // O ideal para POST é retornar 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(alert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertDto.ResponseAlerts> updateAlert(
            @PathVariable Long id,
            @Valid @RequestBody AlertDto.UpdateAlertDto dto) {
        return ResponseEntity.ok(alertService.updateAlert(id, dto));
    }

    @PatchMapping("/{id}/toggle") // Patch é mais adequado para atualizações parciais
    public ResponseEntity<AlertDto.ResponseAlerts> toggleAlert(
            @PathVariable Long id,
            @RequestBody AlertDto.ToggleAlertDto dto) {
        AlertDto.ResponseAlerts updated = alertService.toggleAlert(id, dto);
        return ResponseEntity.ok(updated); // Agora retorna o objeto atualizado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(
            @PathVariable Long id,
            @RequestParam Long userId) {
        alertService.deleteAlert(id, userId);
        return ResponseEntity.noContent().build(); // 204 No Content é o padrão para Delete
    }
}

    // ToDO: Admin endpoints for managing all alerts (get all, get by id, update, delete)

