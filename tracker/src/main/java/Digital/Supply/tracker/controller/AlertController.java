package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @PutMapping("/{id}/resolve")
    public Alert resolveAlert(@PathVariable Long id) {
        return alertService.resolveAlert(id);
    }

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }
}

