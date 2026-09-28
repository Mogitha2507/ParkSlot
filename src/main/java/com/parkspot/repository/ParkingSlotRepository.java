package com.parkspot.repository;

import com.parkspot.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
}
