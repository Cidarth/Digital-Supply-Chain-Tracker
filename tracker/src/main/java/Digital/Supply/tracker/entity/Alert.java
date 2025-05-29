package Digital.Supply.tracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private String type; // DELAY, DAMAGE
    private String message;
    private String createdOn;
    private Boolean resolved;

    public boolean isResolved() {
        return false;
    }
}
