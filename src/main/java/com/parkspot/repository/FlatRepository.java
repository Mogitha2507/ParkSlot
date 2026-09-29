package com.parkspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkspot.entity.Flat;


public interface FlatRepository extends JpaRepository<Flat,Long>{

}