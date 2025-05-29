package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.CheckpointLogDto;
import Digital.Supply.tracker.entity.CheckpointLog;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.exception.ResourceNotFoundException;
import Digital.Supply.tracker.repository.CheckpointLogRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckpointLogServiceTest {

    @InjectMocks
    private CheckpointLogService checkpointLogService;

    @Mock
    private CheckpointLogRepository checkpointLogRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCheckpoint_success() {
        CheckpointLogDto dto = new CheckpointLogDto();
        dto.setShipmentId(1L);
        dto.setLocation("Delhi");
        dto.setStatus("In Transit");

        Shipment shipment = new Shipment();
        shipment.setId(1L);

        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));
        when(checkpointLogRepository.save(any(CheckpointLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CheckpointLog log = checkpointLogService.addCheckpoint(dto);

        assertEquals("Delhi", log.getLocation());
        assertEquals("In Transit", log.getStatus());
        assertEquals(shipment, log.getShipment());
        verify(checkpointLogRepository).save(log);
    }

    @Test
    void testAddCheckpoint_shipmentNotFound() {
        CheckpointLogDto dto = new CheckpointLogDto();
        dto.setShipmentId(404L);

        when(shipmentRepository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> checkpointLogService.addCheckpoint(dto));
    }

    @Test
    void testGetCheckpointsByShipment_success() {
        Long shipmentId = 1L;
        when(shipmentRepository.existsById(shipmentId)).thenReturn(true);

        CheckpointLog log = new CheckpointLog();
        log.setLocation("Hub 1");
        log.setStatus("Dispatched");

        when(checkpointLogRepository.findByShipmentId(shipmentId)).thenReturn(List.of(log));

        List<CheckpointLog> logs = checkpointLogService.getCheckpointsByShipment(shipmentId);

        assertEquals(1, logs.size());
        assertEquals("Hub 1", logs.get(0).getLocation());
        assertEquals("Dispatched", logs.get(0).getStatus());
    }

    @Test
    void testGetCheckpointsByShipment_notFound() {
        Long shipmentId = 999L;
        when(shipmentRepository.existsById(shipmentId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> checkpointLogService.getCheckpointsByShipment(shipmentId));
    }
}
