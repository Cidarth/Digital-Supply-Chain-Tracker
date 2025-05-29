package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.ItemDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.repository.ItemRepository;
import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired private ItemRepository itemRepository;
    @Autowired private UserRepository userRepository;

    public Item addItem(ItemDto dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setCategory(dto.getCategory());
        User supplier = userRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
        item.setSupplier(supplier);
        item.setCreatedDate(java.time.LocalDate.now().toString());
        return itemRepository.save(item);
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ItemDto getItem(Long id) {
        return toDto(itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id))
        );
    }

    private ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setName(item.getName());
        dto.setCategory(item.getCategory());
        dto.setSupplierId(item.getSupplier().getId());
        return dto;
    }
}
