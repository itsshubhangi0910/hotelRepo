package com.example.hotel_details_project.controller;

import com.example.hotel_details_project.model.request.AmenitiesRequest;
import com.example.hotel_details_project.model.request.HotelFilterRequest;
import com.example.hotel_details_project.model.request.HotelRequest;
import com.example.hotel_details_project.model.response.CustomResponse;
import com.example.hotel_details_project.model.response.EntityResponse;
import com.example.hotel_details_project.service.IHotelService;
import com.example.hotel_details_project.service.impl.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/hotelApi")
public class HotelController {

    @Autowired
    private IHotelService iHotelService;

    @Autowired
    private HotelService hotelService;


    @PostMapping("/saveOrUpdateHotel")
    public ResponseEntity<?>saveOrUpdateHotel(@RequestBody List<HotelRequest> hotelRequest){
        try{
            return new ResponseEntity<>(new EntityResponse(iHotelService.saveOrUpdateHotel(hotelRequest),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
    @PostMapping("/getAllHotelByFilter")
     public ResponseEntity<?>getAllHotelFilter(@RequestBody HotelFilterRequest request){
        try{
            Pageable pageable = PageRequest.of(Optional.ofNullable(request.getPageNo()).orElse(0),Optional.ofNullable(request.getPageSize()).orElse(30));
            return new ResponseEntity<>(new EntityResponse(iHotelService.getAllHotelByFilter(request.getHotelAddress(),request.getHotelAmenities(),request.getHotelCharges(),request.getHotelCity(),request.getHotelContactNo(),request.getHotelName(),request.getFilteredColumns(),pageable),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);

        }
    }


    @PostMapping("/saveOrUpdateAmenities")
    public ResponseEntity<?>saveOrUpdateAmenities(@RequestBody AmenitiesRequest amenitiesRequest){
        try{
            return new ResponseEntity<>(new EntityResponse(iHotelService.saveOrUpdateAmenities(amenitiesRequest),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @GetMapping("/getAllHotel")
    public ResponseEntity<?>getAllHotel(){
        try{
            return new ResponseEntity<>(new EntityResponse(iHotelService.getAllHotel(),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
    @GetMapping("/dataToExcel")
    public ResponseEntity<?> download() throws IOException {
        String fileName = "hotel.xlsx";
        ByteArrayInputStream actualData = hotelService.getActualData();
        InputStreamResource file = new InputStreamResource(actualData);
        ResponseEntity<?> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
        return body;
    }
@GetMapping("/exportdataToExcel")
public ResponseEntity<?> exportdataToExcel(HttpServletResponse response) {
    try {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=hotel_data.xlsx");
        return new ResponseEntity(new EntityResponse(hotelService.exportdataToExcel(response), 0), HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity(new CustomResponse(e.getMessage(), -1), HttpStatus.OK);
    }
}
    @PostMapping("/saveExcel")
    public ResponseEntity<?>saveExcel(@RequestParam("file") MultipartFile file){
        try{
            return new ResponseEntity(new EntityResponse(hotelService.save(file),0),HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(new CustomResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }


}

