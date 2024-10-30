package com.example.hotel_details_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class HotelAmenitiesRequest {
    private Long hotelAmenitiesId;
    private Long hotelId;
    private Long amenityId;

}
