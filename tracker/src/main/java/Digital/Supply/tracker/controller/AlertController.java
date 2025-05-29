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
        return alertService.addAlert(alertDto);
    }

    // Get all alerts
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Alert> getAlerts() {
        return alertService.getAllAlerts();
    }

    // Resolve an alert by ID
    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public String resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return "Alert resolved";
    }
}
