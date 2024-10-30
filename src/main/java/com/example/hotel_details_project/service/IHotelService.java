package com.example.hotel_details_project.service;

import com.example.hotel_details_project.model.request.AmenitiesRequest;
import com.example.hotel_details_project.model.request.HotelRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface IHotelService {
    Object saveOrUpdateHotel(List<HotelRequest> hotelRequest);

    Object getAllHotelByFilter(List<String>address, List<String>hotelAmenities, List<Double>hotelCharges, List<String>hotelCity, List<String>hotelContactNo, List<String>hotelName, List<String>FilteredColumns, Pageable pageable) throws IOException;

    Object saveOrUpdateAmenities(AmenitiesRequest amenitiesRequest);

    Object getAllHotel();

    Object exportdataToExcel(HttpServletResponse response) throws IOException;
}
