package Digital.Supply.tracker.service;

import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepo;

    public List<Alert> getAllAlerts() {
        return alertRepo.findAll();
    }

    public Alert resolveAlert(Long id) {
        Alert alert = alertRepo.findById(id).orElseThrow();
        alert.setResolved(true);
        return alertRepo.save(alert);
    }

    public Alert createAlert(Alert alert) {
        return alertRepo.save(alert);
    }
}
