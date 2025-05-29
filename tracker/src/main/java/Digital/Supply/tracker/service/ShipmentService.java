package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.ShipmentDto;
import Digital.Supply.tracker.entity.Item;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.repository.ItemRepository;
import Digital.Supply.tracker.repository.ShipmentRepository;
import Digital.Supply.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {
    @Autowired private ShipmentRepository shipmentRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private UserRepository userRepository;

    public Shipment createShipment(ShipmentDto dto) {
        Shipment shipment = new Shipment();
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow();
        shipment.setItem(item);
        shipment.setFromLocation(dto.getFromLocation());
        shipment.setToLocation(dto.getToLocation());
        shipment.setExpectedDelivery(dto.getExpectedDelivery());
        // Convert String to Enum, robust to casing and stray quotes
        shipment.setCurrentStatus(
                Shipment.Status.valueOf(dto.getCurrentStatus().toUpperCase().replace("\"", "").trim())
        );
        // Assign transporter if provided
        if (dto.getAssignedTransporterId() != null) {
            User transporter = userRepository.findById(dto.getAssignedTransporterId()).orElseThrow();
            shipment.setAssignedTransporter(transporter);
        } else {
            shipment.setAssignedTransporter(null); // Explicitly set to null if not provided
        }
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipment(Long id) {
        return shipmentRepository.findById(id).orElseThrow();
    }

    public void assignTransporter(Long id, Long transporterId) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow();
        User transporter = userRepository.findById(transporterId).orElseThrow();
        shipment.setAssignedTransporter(transporter);
        shipmentRepository.save(shipment);
    }

    public void updateStatus(Long id, String status) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow();
        shipment.setCurrentStatus(
                Shipment.Status.valueOf(status.toUpperCase().replace("\"", "").trim())
        );
        shipmentRepository.save(shipment);
    }
}
