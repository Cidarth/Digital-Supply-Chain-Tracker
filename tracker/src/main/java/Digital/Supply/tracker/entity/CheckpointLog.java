package Digital.Supply.tracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckpointLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private String location;
    private String status; // RECEIVED, IN_TRANSIT, DAMAGED, DELIVERED
    private String timestamp;
}
