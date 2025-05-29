package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.ShipmentDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.exception.ResourceNotFoundException;
import Digital.Supply.tracker.repository.ItemRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import Digital.Supply.tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShipmentServiceTest {

    @InjectMocks
    private ShipmentService shipmentService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShipment_success_withTransporter() {
        ShipmentDto dto = new ShipmentDto();
        dto.setItemId(1L);
        dto.setFromLocation("Warehouse A");
        dto.setToLocation("Warehouse B");
        dto.setExpectedDelivery(String.valueOf(LocalDate.now().plusDays(5)));
        dto.setCurrentStatus("PENDING");
        dto.setAssignedTransporterId(10L);

        Item item = new Item();
        item.setId(1L);

        User transporter = new User();
        transporter.setId(10L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findById(10L)).thenReturn(Optional.of(transporter));
        when(shipmentRepository.save(any(Shipment.class))).thenAnswer(i -> i.getArgument(0));

        Shipment shipment = shipmentService.createShipment(dto);

        assertNotNull(shipment);
        assertEquals("Warehouse A", shipment.getFromLocation());
        assertEquals(Shipment.Status.PENDING, shipment.getCurrentStatus());
        assertEquals(transporter, shipment.getAssignedTransporter());
    }

    @Test
    void testCreateShipment_itemNotFound() {
        ShipmentDto dto = new ShipmentDto();
        dto.setItemId(999L);

        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shipmentService.createShipment(dto));
    }

    @Test
    void testCreateShipment_transporterNotFound() {
        ShipmentDto dto = new ShipmentDto();
        dto.setItemId(1L);
        dto.setAssignedTransporterId(999L);
        dto.setFromLocation("Warehouse A");
        dto.setToLocation("Store B");
        dto.setExpectedDelivery("2025-06-01");
        dto.setCurrentStatus("PENDING");

        Item item = new Item();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shipmentService.createShipment(dto));
    }


    @Test
    void testGetAllShipments() {
        when(shipmentRepository.findAll()).thenReturn(List.of(new Shipment(), new Shipment()));

        List<Shipment> shipments = shipmentService.getAllShipments();

        assertEquals(2, shipments.size());
        verify(shipmentRepository).findAll();
    }

    @Test
    void testGetShipment_found() {
        Shipment shipment = new Shipment();
        shipment.setId(1L);

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));

        Shipment result = shipmentService.getShipment(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetShipment_notFound() {
        when(shipmentRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shipmentService.getShipment(404L));
    }

    @Test
    void testAssignTransporter_success() {
        Shipment shipment = new Shipment();
        shipment.setId(1L);

        User transporter = new User();
        transporter.setId(20L);

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(userRepository.findById(20L)).thenReturn(Optional.of(transporter));

        shipmentService.assignTransporter(1L, 20L);

        assertEquals(transporter, shipment.getAssignedTransporter());
        verify(shipmentRepository).save(shipment);
    }

    @Test
    void testUpdateStatus_success() {
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        shipment.setCurrentStatus(Shipment.Status.PENDING);

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));

        shipmentService.updateStatus(1L, "SHIPPED");

        assertEquals(Shipment.Status.SHIPPED, shipment.getCurrentStatus());
        verify(shipmentRepository).save(shipment);
    }

    @Test
    void testUpdateStatus_invalidShipmentId() {
        when(shipmentRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shipmentService.updateStatus(100L, "SHIPPED"));
    }
}
