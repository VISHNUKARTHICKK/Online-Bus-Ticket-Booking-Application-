package com.bbs.gobus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbs.gobus.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long id);


    
}