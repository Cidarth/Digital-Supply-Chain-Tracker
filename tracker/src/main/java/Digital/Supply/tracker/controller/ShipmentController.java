package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.dto.ShipmentDto;
import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    @Autowired private ShipmentService shipmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPPLIER','ADMIN')")
    public Shipment createShipment(@RequestBody ShipmentDto dto) {
        return shipmentService.createShipment(dto);
    }

    @PutMapping("/{id}/assign")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    public String assignTransporter(@PathVariable Long id, @RequestBody Long transporterId) {
        shipmentService.assignTransporter(id, transporterId);
        return "Transporter assigned";
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER','TRANSPORTER','MANAGER')")
    public List<Shipment> getShipments() {
        return shipmentService.getAllShipments();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER','TRANSPORTER','MANAGER')")
    public Shipment getShipment(@PathVariable Long id) {
        return shipmentService.getShipment(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('TRANSPORTER','ADMIN')")
    public String updateStatus(@PathVariable Long id, @RequestBody String status) {
        shipmentService.updateStatus(id, status);
        return "Shipment status updated";
    }
}
