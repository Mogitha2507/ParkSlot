package com.parkspot.repository;

import com.parkspot.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlatRepository extends JpaRepository<Flat, Long> {
}
