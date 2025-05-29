package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.dto.AlertDto;
import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    // Create a new alert
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER','TRANSPORTER','MANAGER')")
    public Alert addAlert(@RequestBody AlertDto alertDto) {
        try {
            return alertService.addAlert(alertDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add alert: " + e.getMessage());
        }
    }

    // Get all alerts
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Alert> getAlerts() {
        try {
            return alertService.getAllAlerts();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve alerts: " + e.getMessage());
        }
      //  return alertService.getAllAlerts();
    }

    // Resolve an alert by ID
    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public String resolveAlert(@PathVariable Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Alert ID cannot be null");
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid alert ID: " + e.getMessage());
        }
        alertService.resolveAlert(id);
        return "Alert resolved";
    }
}
