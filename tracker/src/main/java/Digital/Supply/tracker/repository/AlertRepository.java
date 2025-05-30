package Digital.Supply.tracker.repository;

import Digital.Supply.tracker.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {}
