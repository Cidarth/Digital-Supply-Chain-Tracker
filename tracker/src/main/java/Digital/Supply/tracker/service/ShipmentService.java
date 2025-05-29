package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.ShipmentDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.repository.ItemRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShipmentService {
    @Autowired private ShipmentRepository shipmentRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private UserRepository userRepository;

    public Shipment createShipment(ShipmentDto dto) {
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + dto.getItemId()));
        Shipment shipment = new Shipment();
        shipment.setItem(item);
        shipment.setFromLocation(dto.getFromLocation());
        shipment.setToLocation(dto.getToLocation());
        shipment.setExpectedDelivery(dto.getExpectedDelivery());
        shipment.setCurrentStatus(
                Shipment.Status.valueOf(dto.getCurrentStatus().toUpperCase().replace("\"", "").trim())
        );
        if (dto.getAssignedTransporterId() != null) {
            User transporter = userRepository.findById(dto.getAssignedTransporterId())
                    .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + dto.getAssignedTransporterId()));
            shipment.setAssignedTransporter(transporter);
        } else {
            shipment.setAssignedTransporter(null);
        }
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipment(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
    }

    public void assignTransporter(Long id, Long transporterId) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        User transporter = userRepository.findById(transporterId)
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + transporterId));
        shipment.setAssignedTransporter(transporter);
        shipmentRepository.save(shipment);
    }

    public void updateStatus(Long id, String status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        shipment.setCurrentStatus(
                Shipment.Status.valueOf(status.toUpperCase().replace("\"", "").trim())
        );
        shipmentRepository.save(shipment);
    }
}
