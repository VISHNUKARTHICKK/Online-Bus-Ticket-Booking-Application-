package com.bbs.gobus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bbs.gobus.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    void save(List<Passenger> ageList);

   @Query("SELECT p.seatNumber FROM Passenger p WHERE p.booking.bus.busName = :busName")
List<Integer> findSeatNumbersByBusName(@Param("busName") String busName);


}
