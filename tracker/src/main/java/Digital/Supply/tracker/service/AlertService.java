package Digital.Supply.tracker.service;

import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepo;

    public AlertService(AlertRepository alertRepo) {
        this.alertRepo = alertRepo;
    }

    public List<Alert> getAllAlerts() {
        return alertRepo.findAll();
    }

    public Alert resolveAlert(Long id) {
        Alert alert = alertRepo.findById(id).orElseThrow();
        alert.setResolved(true);
        return alertRepo.save(alert);
    }
}
