package com.example.hotel_details_project.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hotel_db")
public class Hotel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "hotel_name")
    private String hotelName;

    @Column(name = "hotel_address")
    private String hotelAddress;

    @Column(name = "hotel_city")
    private String hotelCity;

    @Column(name = "hotel_contact_no")
    private String hotelContactNo;

    @Column(name = "hotel_charges")
    private Double hotelCharges;


    @Column(name = "hotel_amenities")
    private String hotelAmenities;


    @Column(name = "is_deleted")
    private Boolean isDeleted=false;

    @Column(name = "is_active")
    private  Boolean isActive;

    @CreationTimestamp
    @Column(updatable = false,name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;




}
