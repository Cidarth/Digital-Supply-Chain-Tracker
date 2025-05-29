package Digital.Supply.tracker.dto;

import lombok.Data;

@Data
public class CheckpointLogDto {
    private Long shipmentId;
    private String location;
    private String status;
}
