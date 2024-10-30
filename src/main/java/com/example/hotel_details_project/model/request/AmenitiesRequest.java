package com.example.hotel_details_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AmenitiesRequest {
    private Long amenityId;
    private Long hotelId;
    private String amenityName;

}
