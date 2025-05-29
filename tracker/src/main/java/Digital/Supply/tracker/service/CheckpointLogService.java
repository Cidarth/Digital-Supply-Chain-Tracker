package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.CheckpointLogDto;
import Digital.Supply.tracker.entity.CheckpointLog;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.repository.CheckpointLogRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CheckpointLogService {
    @Autowired private CheckpointLogRepository checkpointLogRepository;
    @Autowired private ShipmentRepository shipmentRepository;

    public CheckpointLog addCheckpoint(CheckpointLogDto dto) {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId()).orElseThrow(() -> new EntityNotFoundException("Shipment not found with id: " + dto.getShipmentId()));
        CheckpointLog log = new CheckpointLog();
        log.setShipment(shipment);
        log.setLocation(dto.getLocation());
        log.setStatus(dto.getStatus());
        log.setTimestamp(java.time.LocalDateTime.now().toString());
        return checkpointLogRepository.save(log);
    }

    public List<CheckpointLog> getCheckpointsByShipment(Long shipmentId) {
        return checkpointLogRepository.findByShipmentId(shipmentId);
    }
}
