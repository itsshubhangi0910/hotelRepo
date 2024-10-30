package com.example.hotel_details_project.repository;

import com.example.hotel_details_project.model.HotelAmenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface HotelAmenitiesRepository extends JpaRepository<HotelAmenities,Long> {
    boolean existsByHotelId(Long hotelId);

    @Modifying
    @Transactional
    void deleteAllByHotelId(Long hotelId);
}
