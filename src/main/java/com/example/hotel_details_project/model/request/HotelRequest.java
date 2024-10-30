package com.example.hotel_details_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HotelRequest {
    private Long hotelId;
    private String hotelName;
    private String hotelAddress;
    private String hotelCity;
    private String hotelContactNo;
    private Double hotelCharges;
    private String hotelAmenities;
    private List<Long>amenityIds;
}
