package com.example.hotel_details_project.model.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class HotelFilterRequest {
    private List<String> hotelAddress;
    private List<String> hotelName;
    private List<String> hotelCity;
    private List<String> hotelContactNo;
    private List<Double> hotelCharges;
    private List<String> hotelAmenities;
    private List<Long> amenityIds;
    private Integer pageNo;
    private Integer pageSize;

    private List<String> FilteredColumns;
   /* @Getter
    @Setter
    public static class FilteredColumns {

    }*/
}
