package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.dto.ItemDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired private ItemService itemService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    public Item addItem(@RequestBody ItemDto dto) {
        return itemService.addItem(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    public List<ItemDto> getItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    public ItemDto getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }
}
