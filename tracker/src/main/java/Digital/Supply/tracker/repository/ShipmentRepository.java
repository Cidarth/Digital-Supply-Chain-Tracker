package Digital.Supply.tracker.repository;

import Digital.Supply.tracker.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByCurrentStatusNot(String status);
}
