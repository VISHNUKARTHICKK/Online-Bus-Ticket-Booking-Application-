package com.bbs.gobus.entity;

// Passenger.java
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // Getters & Setters
}
