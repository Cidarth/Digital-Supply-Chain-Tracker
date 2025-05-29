package Digital.Supply.tracker.service;

import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.entity.Shipment.Status;
import Digital.Supply.tracker.repository.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDelayedShipments() {
        Shipment shipment1 = new Shipment();
        shipment1.setCurrentStatus(Status.DELAYED);

        when(shipmentRepository.findByCurrentStatus(Status.DELAYED))
                .thenReturn(List.of(shipment1));

        List<Shipment> delayedShipments = reportService.getDelayedShipments();

        assertEquals(1, delayedShipments.size());
        assertEquals(Status.DELAYED, delayedShipments.get(0).getCurrentStatus());
        verify(shipmentRepository).findByCurrentStatus(Status.DELAYED);
    }

    @Test
    void testGetAllShipments() {
        when(shipmentRepository.findAll()).thenReturn(List.of(new Shipment(), new Shipment()));

        List<Shipment> allShipments = reportService.getAllShipment();

        assertEquals(2, allShipments.size());
        verify(shipmentRepository).findAll();
    }
}
