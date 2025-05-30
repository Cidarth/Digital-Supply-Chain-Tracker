package Digital.Supply.tracker.repository;

import Digital.Supply.tracker.entity.CheckpointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CheckpointLogRepository extends JpaRepository<CheckpointLog, Long> {
    List<CheckpointLog> findByShipmentId(Long shipmentId);
}