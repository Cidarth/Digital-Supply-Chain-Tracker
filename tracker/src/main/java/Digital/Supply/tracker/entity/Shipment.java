package Digital.Supply.tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int itemId;
    private String fromLocation;
    private String toLocation;
    private LocalDateTime expectedDelivery;
    private String currentStatus;
    private int assignedTransporter;
}
