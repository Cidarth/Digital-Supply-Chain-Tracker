package Digital.Supply.tracker.scheduler;

import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.repository.AlertRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DelayChecker {

    private final ShipmentRepository shipmentRepo;
    private final AlertRepository alertRepo;

    @Scheduled(fixedRate = 300000)
    public void checkForDelayedShipments() {
        List<Shipment> pendingShipments = shipmentRepo.findByCurrentStatusNot("DELIVERED");

        for (Shipment shipment : pendingShipments) {
            if (shipment.getExpectedDelivery() != null && 
                LocalDateTime.parse(shipment.getExpectedDelivery()).isBefore(LocalDateTime.now())) {
                boolean alreadyAlerted = alertRepo.existsByShipmentIdAndTypeAndResolvedFalse(
                        shipment.getId(), "DELAY");

                if (!alreadyAlerted) {
                    Alert alert = new Alert();
                    alert.setShipment(shipment);
                    alert.setType("DELAY");
                    alert.setMessage("Shipment delayed beyond expected time.");
                    alert.setCreatedOn(LocalDateTime.now().toString());
                    alert.setResolved(false);

                    alertRepo.save(alert);
                    log.info("Delay alert triggered for shipment id: {}", shipment.getId());
                }
            }
        }
    }
}