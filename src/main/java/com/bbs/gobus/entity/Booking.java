package com.bbs.gobus.entity;

// Booking.java
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // foreign key in booking table
    private User user;

    private String email;
    private String phone;
    private int passengers;

      @ManyToOne
@JoinColumn(name = "bus_id")
private BusDetails bus;   // Relation to bus

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengerList;

    // Route info
    private String boardingPoint;
    private String dropPoint;
    private String busName;
    private String date;

}
