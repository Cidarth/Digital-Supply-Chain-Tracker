package Digital.Supply.tracker;

import Digital.Supply.tracker.dto.ItemDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.exception.ResourceNotFoundException;
import Digital.Supply.tracker.repository.ItemRepository;
import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class TrackerApplicationTests {

	@Autowired
	private ItemService itemService;

	@MockBean
	private ItemRepository itemRepository;

	@MockBean
	private UserRepository userRepository;

	private User supplier;
	private Item item;

	@BeforeEach
	void setUp() {
		supplier = new User();
		supplier.setId(1L);
		supplier.setEmail("idk@idk.com");

		item = new Item();
		item.setId(10L);
		item.setName("sheet");
		item.setCategory("plastic");
		item.setSupplier(supplier);
	}

	@Test
	void contextLoads() {

	}

	@Test
	void getItem_whenItemExists_returnsItemDto() {
		Mockito.when(itemRepository.findById(10L)).thenReturn(Optional.of(item));
		ItemDto dto = itemService.getItem(10L);
		assertThat(dto).isNotNull();
		assertThat(dto.getName()).isEqualTo("sheet");
		assertThat(dto.getCategory()).isEqualTo("plastic");
		assertThat(dto.getSupplierId()).isEqualTo(1L);
	}

	@Test
	void getItem_whenItemNotFound_throwsException() {
		Mockito.when(itemRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> itemService.getItem(99L));
	}

	@Test
	void addItem_whenSupplierExists_savesItem() {
		ItemDto dto = new ItemDto();
		dto.setName("sheet");
		dto.setCategory("plastic");
		dto.setSupplierId(1L);

		Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(supplier));
		Mockito.when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Item saved = itemService.addItem(dto);
		assertThat(saved).isNotNull();
		assertThat(saved.getName()).isEqualTo("sheet");
		assertThat(saved.getCategory()).isEqualTo("plastic");
		assertThat(saved.getSupplier().getId()).isEqualTo(1L);
	}

	@Test
	void addItem_whenSupplierNotFound_throwsException() {
		ItemDto dto = new ItemDto();
		dto.setName("sheet");
		dto.setCategory("plastic");
		dto.setSupplierId(2L);

		Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> itemService.addItem(dto));
	}
}
