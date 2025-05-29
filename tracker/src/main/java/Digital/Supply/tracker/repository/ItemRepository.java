package Digital.Supply.tracker.repository;

import Digital.Supply.tracker.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {}
