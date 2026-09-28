package com.parkspot.repository;

import com.parkspot.entity.VisitorVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitorVehicleRepository extends JpaRepository<VisitorVehicle, Long> {

    List<VisitorVehicle> findByExitTimeIsNull();

    List<VisitorVehicle> findByEntryTimeBetween(
            LocalDateTime start, LocalDateTime end);
}
