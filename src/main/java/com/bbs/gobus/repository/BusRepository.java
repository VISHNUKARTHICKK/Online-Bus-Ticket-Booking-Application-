package com.bbs.gobus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbs.gobus.entity.BusDetails;

public interface BusRepository extends JpaRepository<BusDetails, Long> {

    List<BusDetails> findByBoardingPointAndDropPointAndDate(String boardingPoint, String dropPoint, String date);
   

}
