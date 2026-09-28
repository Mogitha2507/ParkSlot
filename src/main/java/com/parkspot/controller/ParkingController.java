package com.parkspot.controller;

import com.parkspot.entity.*;
import com.parkspot.service.ParkingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ParkingController {

    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @PostMapping("/flats")
    @ResponseStatus(HttpStatus.CREATED)
    public Flat addFlat(@Valid @RequestBody Flat flat) {
        return service.addFlat(flat);
    }

    @PostMapping("/slots")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSlot addSlot(@Valid @RequestBody ParkingSlot slot) {
        return service.addSlot(slot);
    }

    @PostMapping("/entries")
    @ResponseStatus(HttpStatus.CREATED)
    public VisitorVehicle entry(@Valid @RequestBody EntryRequest request) {
        return service.entry(
                request.vehicleNumber(),
                request.flatId(),
                request.slotId());
    }

    @PutMapping("/exits/{vehicleId}")
    public VisitorVehicle exit(@PathVariable Long vehicleId) {
        return service.exit(vehicleId);
    }

    @GetMapping("/occupied")
    public List<VisitorVehicle> occupied() {
        return service.occupied();
    }

    @GetMapping("/daily-report")
    public List<VisitorVehicle> dailyReport() {
        return service.dailyReport();
    }

    @GetMapping("/slots")
    public List<ParkingSlot> slots() {
        return service.slots();
    }

    @GetMapping("/dashboard")
    public Map<String, Integer> dashboard() {
        return Map.of(
                "totalVisitorRecords", service.dailyReport().size(),
                "currentlyOccupied", service.occupied().size()
        );
    }
    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(@PathVariable Long id) {
    service.deleteVehicle(id);
    }

    public record EntryRequest(
            @NotBlank String vehicleNumber,
            @NotNull Long flatId,
            @NotNull Long slotId) {
    }
}
