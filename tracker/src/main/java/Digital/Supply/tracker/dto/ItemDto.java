package Digital.Supply.tracker.dto;

import lombok.Data;

@Data
public class ItemDto {
    private String name;
    private String category;
    private Long supplierId;
}
