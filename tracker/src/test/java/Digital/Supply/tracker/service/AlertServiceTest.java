package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.AlertDto;
import Digital.Supply.tracker.entity.Alert;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.repository.AlertRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private AlertService alertService;

    private Shipment shipment;
    private Alert alert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shipment = new Shipment();
        shipment.setId(1L);

        alert = new Alert();
        alert.setId(100L);
        alert.setShipment(shipment);
        alert.setType("DELAY");
        alert.setMessage("Delayed at checkpoint");
        alert.setCreatedOn("2025-05-29");
        alert.setResolved(false);
    }

    @Test
    void addAlert_whenShipmentExists_savesAlert() {
        AlertDto dto = new AlertDto();
        dto.setShipmentId(1L);
        dto.setType("DELAY");
        dto.setMessage("Delayed at checkpoint");

        given(shipmentRepository.findById(1L)).willReturn(Optional.of(shipment));
        given(alertRepository.save(any(Alert.class))).willAnswer(invocation -> invocation.getArgument(0));

        Alert saved = alertService.addAlert(dto);

        assertThat(saved).isNotNull();
        assertThat(saved.getType()).isEqualTo("DELAY");
        assertThat(saved.getMessage()).isEqualTo("Delayed at checkpoint");
        assertThat(saved.getShipment()).isEqualTo(shipment);

    }

    @Test
    void addAlert_whenShipmentNotFound_throwsException() {
        AlertDto dto = new AlertDto();
        dto.setShipmentId(2L);
        dto.setType("DELAY");
        dto.setMessage("Delayed at checkpoint");

        given(shipmentRepository.findById(2L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alertService.addAlert(dto));
    }

    @Test
    void getAllAlerts_returnsListOfAlerts() {
        List<Alert> alerts = new ArrayList<>();
        alerts.add(alert);

        given(alertRepository.findAll()).willReturn(alerts);

        List<Alert> result = alertService.getAllAlerts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(100L);
    }

    @Test
    void resolveAlert_whenAlertExists_setsResolvedTrue() {
        given(alertRepository.findById(100L)).willReturn(Optional.of(alert));
        given(alertRepository.save(any(Alert.class))).willAnswer(invocation -> invocation.getArgument(0));

        alertService.resolveAlert(100L);


    }

    @Test
    void resolveAlert_whenAlertNotFound_throwsException() {
        given(alertRepository.findById(200L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> alertService.resolveAlert(200L));
    }
}