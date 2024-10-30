package com.example.hotel_details_project.repository;

import com.example.hotel_details_project.model.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities,Long> {

}
