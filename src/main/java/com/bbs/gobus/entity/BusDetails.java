package com.bbs.gobus.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="BusDetails")
public class BusDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String coach;
    @Column(nullable = false)
    private String type;
     @Column(nullable = false)
    private String busName;
    @Column(nullable = false)
    private String boardingPoint;
    @Column(nullable = false)
    private String dropPoint;
    @Column(nullable = false)
    private Long seats;
    @Column(nullable = false)
    private String date;


}
