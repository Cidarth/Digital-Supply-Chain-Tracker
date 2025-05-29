package Digital.Supply.tracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private String fromLocation;
    private String toLocation;
    private String expectedDelivery;

    @Enumerated(EnumType.STRING)
    private Status currentStatus;

    @ManyToOne
    @JoinColumn(name = "assigned_transporter_id", nullable = true)
    private User assignedTransporter;

    public enum Status {
        CREATED, IN_TRANSIT, DELIVERED, PENDING, SHIPPED, DELAYED
    }
}
