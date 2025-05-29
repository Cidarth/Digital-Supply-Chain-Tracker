package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.ItemDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.exception.ResourceNotFoundException;
import Digital.Supply.tracker.repository.ItemRepository;
import Digital.Supply.tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItem_success() {
        ItemDto dto = new ItemDto();
        dto.setName("Steel Rod");
        dto.setCategory("Raw Material");
        dto.setSupplierId(100L);

        User supplier = new User();
        supplier.setId(100L);

        when(userRepository.findById(100L)).thenReturn(Optional.of(supplier));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        Item item = itemService.addItem(dto);

        assertNotNull(item);
        assertEquals("Steel Rod", item.getName());
        assertEquals("Raw Material", item.getCategory());
        assertEquals(supplier, item.getSupplier());
        verify(itemRepository).save(item);
    }

    @Test
    void testAddItem_supplierNotFound() {
        ItemDto dto = new ItemDto();
        dto.setSupplierId(999L);

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemService.addItem(dto));
    }

    @Test
    void testGetAllItems() {
        Item item1 = new Item();
        item1.setName("Bolt");
        item1.setCategory("Fasteners");
        User supplier = new User();
        supplier.setId(1L);
        item1.setSupplier(supplier);

        when(itemRepository.findAll()).thenReturn(List.of(item1));

        List<ItemDto> result = itemService.getAllItems();

        assertEquals(1, result.size());
        assertEquals("Bolt", result.get(0).getName());
        assertEquals("Fasteners", result.get(0).getCategory());
        assertEquals(1L, result.get(0).getSupplierId());
    }

    @Test
    void testGetItem_success() {
        Item item = new Item();
        item.setName("Nut");
        item.setCategory("Fasteners");
        User supplier = new User();
        supplier.setId(2L);
        item.setSupplier(supplier);

        when(itemRepository.findById(10L)).thenReturn(Optional.of(item));

        ItemDto dto = itemService.getItem(10L);

        assertEquals("Nut", dto.getName());
        assertEquals("Fasteners", dto.getCategory());
        assertEquals(2L, dto.getSupplierId());
    }

    @Test
    void testGetItem_notFound() {
        when(itemRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemService.getItem(404L));
    }
}
