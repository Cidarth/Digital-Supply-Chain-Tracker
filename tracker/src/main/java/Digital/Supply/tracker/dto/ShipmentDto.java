package Digital.Supply.tracker.dto;

import lombok.Data;

@Data
public class ShipmentDto {
    private Long itemId;
    private String fromLocation;
    private String toLocation;
    private String expectedDelivery;
    private String currentStatus; // Should be: CREATED, IN_TRANSIT, DELIVERED, DELAYED
    private Long assignedTransporterId; // Optional: set if you want to assign a transporter on creation
}
