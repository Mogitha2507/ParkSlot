package com.parkspot.service;

import com.parkspot.entity.*;
import com.parkspot.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    private final VisitorVehicleRepository vehicleRepo;
    private final ParkingSlotRepository slotRepo;
    private final FlatRepository flatRepo;

    public ParkingService(VisitorVehicleRepository vehicleRepo,
                          ParkingSlotRepository slotRepo,
                          FlatRepository flatRepo) {
        this.vehicleRepo = vehicleRepo;
        this.slotRepo = slotRepo;
        this.flatRepo = flatRepo;
    }

    public VisitorVehicle entry(String vehicleNumber, Long flatId, Long slotId) {

        ParkingSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Parking slot not found"));

        Flat flat = flatRepo.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        if (slot.isOccupied()) {
            throw new RuntimeException("Parking slot is already occupied");
        }

        VisitorVehicle vehicle = new VisitorVehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setFlat(flat);
        vehicle.setSlot(slot);
        vehicle.setEntryTime(LocalDateTime.now());

        slot.setOccupied(true);
        slotRepo.save(slot);

        return vehicleRepo.save(vehicle);
    }

    public VisitorVehicle exit(Long vehicleId) {

        VisitorVehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle record not found"));

        if (vehicle.getEntryTime() == null) {
            throw new RuntimeException("Entry time must be recorded before exit");
        }

        if (vehicle.getExitTime() != null) {
            throw new RuntimeException("Vehicle has already exited");
        }

        vehicle.setExitTime(LocalDateTime.now());

        ParkingSlot slot = vehicle.getSlot();
        slot.setOccupied(false);
        slotRepo.save(slot);

        return vehicleRepo.save(vehicle);
    }

    public List<VisitorVehicle> occupied() {
        return vehicleRepo.findByExitTimeIsNull();
    }

    public List<VisitorVehicle> dailyReport() {
        LocalDate today = LocalDate.now();
        return vehicleRepo.findByEntryTimeBetween(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay());
    }

    public List<ParkingSlot> slots() {
        return slotRepo.findAll();
    }

    public Flat addFlat(Flat flat) {
        return flatRepo.save(flat);
    }

    public ParkingSlot addSlot(ParkingSlot slot) {
        slot.setOccupied(false);
        return slotRepo.save(slot);
    }
    public void deleteVehicle(Long id) {
    vehicleRepo.deleteById(id);
}
}
