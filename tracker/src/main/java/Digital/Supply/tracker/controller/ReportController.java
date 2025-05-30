package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.entity.Shipment;
import Digital.Supply.tracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/delayed-shipments")
    public List<Shipment> getAllDelayedShipments() {
        return reportService.getDelayedShipments();
    }
    @GetMapping("/delivery-performance")
    public List<Shipment> getAllShipment(){
        return reportService.getAllShipment();
    }
}