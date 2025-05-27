package Digital.Supply.tracker.scheduler;

import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.repository.AlertRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DelayChecker {

    private final ShipmentRepository shipmentRepo;
    private final AlertRepository alertRepo;
    private static final Logger log = LoggerFactory.getLogger(DelayChecker.class);

    public DelayChecker(ShipmentRepository shipmentRepo, AlertRepository alertRepo) {
        this.shipmentRepo = shipmentRepo;
        this.alertRepo = alertRepo;
    }

    @Scheduled(fixedRate = 300000)
    public void checkForDelayedShipments() {
        List<Shipment> pendingShipments = shipmentRepo.findByCurrentStatusNot("DELIVERED");

        for (Shipment shipment : pendingShipments) {
            if (shipment.getExpectedDelivery() != null && shipment.getExpectedDelivery().isBefore(LocalDateTime.now())) {
                boolean alreadyAlerted = alertRepo.existsByShipmentIdAndTypeAndResolvedFalse(
                        shipment.getId(), "DELAY");

                if (!alreadyAlerted) {
                    Alert alert = new Alert();
                    alert.setShipment(shipment);
                    alert.setType("DELAY");
                    alert.setMessage("Shipment delayed beyond expected time.");
                    alert.setCreatedOn(LocalDateTime.now());
                    alert.setResolved(false);

                    alertRepo.save(alert);
                    log.info("Delay alert triggered for shipment id: {}", shipment.getId());
                }
            }
        }
    }
}
