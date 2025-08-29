package com.bbs.gobus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbs.gobus.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    void save(List<Passenger> ageList);}
