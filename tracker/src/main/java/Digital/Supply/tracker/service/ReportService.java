package Digital.Supply.tracker.service;
import java.util.List;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<Shipment> getDelayedShipments() {
        return shipmentRepository.findByCurrentStatus(Shipment.Status.DELAYED);
    }
    public List<Shipment> getAllShipment(){
        return shipmentRepository.findAll();
    }

}