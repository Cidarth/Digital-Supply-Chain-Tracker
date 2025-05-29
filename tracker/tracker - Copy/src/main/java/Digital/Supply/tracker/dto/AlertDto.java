package Digital.Supply.tracker.dto;

import lombok.Data;

@Data
public class AlertDto {
    private Long shipmentId;
    private String type;
    private String message;
}
