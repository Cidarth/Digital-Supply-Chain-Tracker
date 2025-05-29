package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.AlertDto;
import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.repository.AlertRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    // Create a new alert
    public Alert addAlert(AlertDto dto) {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                .orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + dto.getShipmentId()));
        Alert alert = new Alert();
        alert.setShipment(shipment);
        alert.setType(dto.getType());
        alert.setMessage(dto.getMessage());
        alert.setCreatedOn(java.time.LocalDate.now().toString());
        alert.setResolved(false);
        return alertRepository.save(alert);
    }

    // List all alerts
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // Resolve an alert by ID
    public void resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found with id: " + id));
        alert.setResolved(true);
        alertRepository.save(alert);
    }
}
