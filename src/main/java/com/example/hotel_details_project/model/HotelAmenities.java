package com.example.hotel_details_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel_amenities")
public class HotelAmenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "hotel_amenities_id")
    private Long hotelAmenitiesId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "amenity_id")
    private Long amenityId;

}
