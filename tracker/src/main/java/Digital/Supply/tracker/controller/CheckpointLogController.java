package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.dto.CheckpointLogDto;
import Digital.Supply.tracker.entity.CheckpointLog;
import Digital.Supply.tracker.service.CheckpointLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkpoints")
public class CheckpointLogController {
    @Autowired private CheckpointLogService checkpointLogService;

    @PostMapping
    @PreAuthorize("hasAnyRole('TRANSPORTER','ADMIN','MANAGER')")
    public CheckpointLog addCheckpoint(@RequestBody CheckpointLogDto dto) {
        return checkpointLogService.addCheckpoint(dto);
    }

    @GetMapping("/shipment/{id}")
    @PreAuthorize("hasAnyRole('TRANSPORTER','ADMIN','MANAGER')")
    public List<CheckpointLog> getCheckpoints(@PathVariable Long id) {
        return checkpointLogService.getCheckpointsByShipment(id);
    }
}
