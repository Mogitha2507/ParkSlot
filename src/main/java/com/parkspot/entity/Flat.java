package com.parkspot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String flatNumber;

    public Long getId() {
        return id;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }
}
