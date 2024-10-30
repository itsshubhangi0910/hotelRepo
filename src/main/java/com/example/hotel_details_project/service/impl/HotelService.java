package com.example.hotel_details_project.service.impl;
import com.example.hotel_details_project.model.Amenities;
import com.example.hotel_details_project.model.Hotel;
import com.example.hotel_details_project.model.HotelAmenities;
import com.example.hotel_details_project.model.PageDto;
import com.example.hotel_details_project.model.request.AmenitiesRequest;
import com.example.hotel_details_project.model.request.HotelRequest;
import com.example.hotel_details_project.model.response.HotelResponse;
import com.example.hotel_details_project.repository.AmenitiesRepository;
import com.example.hotel_details_project.repository.HotelAmenitiesRepository;
import com.example.hotel_details_project.repository.HotelRepository;
import com.example.hotel_details_project.service.IHotelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelService implements IHotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    private HotelAmenitiesRepository hotelAmenitiesRepository;

    @Override
    public Object saveOrUpdateHotel(List<HotelRequest> requestList) {
        for (HotelRequest hotelRequest:requestList){
        if (hotelRepository.existsById(hotelRequest.getHotelId())) {
            Hotel hotel = hotelRepository.findById(hotelRequest.getHotelId()).get();
            saveOrUpdateHotel(hotel, hotelRequest);

        } else {
            Hotel hotel = new Hotel();
            hotel.setIsActive(true);
            hotel.setIsDeleted(false);
            saveOrUpdateHotel(hotel, hotelRequest);

        }
    }
        return "success";
    }

    @Override
    public Object getAllHotelByFilter(List<String> hotelAddress, List<String> hotelAmenities, List<Double> hotelCharges, List<String> hotelCity, List<String> hotelContactNo, List<String> hotelName, List<String> filteredColumns, Pageable pageable) throws IOException {
        List<HotelResponse> hotelResponseList = new ArrayList<>();
        if (hotelAddress != null && !hotelAddress.isEmpty()){
            hotelAddress= hotelAddress.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }else{
            hotelAddress=null;
        }
        if (hotelAmenities != null && !hotelAmenities.isEmpty()){
            hotelAmenities = hotelAmenities.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }else{
            hotelAmenities=null;
        }
        /*if (hotelCharges != null && !hotelCharges.isEmpty()){
            hotelCharges = hotelCharges.stream()
                    .collect(Collectors.toList());
        }*/
        if (hotelCity != null && !hotelCity.isEmpty()){
            hotelCity = hotelCity.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }else{
            hotelCity=null;
        }
        if (hotelContactNo != null && !hotelContactNo.isEmpty()){
            hotelContactNo = hotelContactNo.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }else{
            hotelContactNo=null;
        }
        if (hotelName != null && !hotelName.isEmpty()){
            hotelName = hotelName.stream()
                    .map(String::toLowerCase)
                    .map(String::trim)
                    .collect(Collectors.toList());
        }else{
            hotelName=null;
        }
        List<Hotel> hotelList = new ArrayList<>();
        Page<Hotel> hotelPage = new PageImpl<>(hotelList);
        System.out.println("hotelAddress-->"+hotelAddress+" hotelAmenities-->"+hotelAmenities+ " hotelCity-->"+hotelCity+" hotelContactNo-->"+hotelContactNo+
        " hotelName-->"+hotelName);

        hotelPage = hotelRepository.searchAllHotelFilter(hotelAddress,hotelAmenities,hotelCharges,hotelCity,hotelContactNo,hotelName,pageable);

        //List<HotelResponse> hotelResponses = new ArrayList<>();
        if (hotelPage != null){
            for (Hotel h:hotelPage){
                HotelResponse response = new HotelResponse();
                response.setHotelAddress(h.getHotelAddress());
                response.setHotelAmenities(h.getHotelAmenities());
                response.setHotelCharges(h.getHotelCharges());
                response.setHotelCity(h.getHotelCity());
                response.setHotelContactNo(h.getHotelContactNo());
                response.setHotelName(h.getHotelName());
                hotelResponseList.add(response);
            }
        }
        List<Map<String, String>> hotelData = hotelPage.stream()
                .map(h -> {
                        Map<String, String> map = new HashMap<>();
                    for (String cl:filteredColumns) {
                        if (cl.equalsIgnoreCase("HotelName")) {
                            map.put("hotelName", h.getHotelName());
                        }
                        if (cl.equalsIgnoreCase("HotelCity")) {
                            map.put("hotelCity", h.getHotelCity());
                        }
                        if (cl.equalsIgnoreCase("HotelAddress")) {
                            map.put("hotelAddress", h.getHotelAddress());
                        }
                        if (cl.equalsIgnoreCase("HotelAmenities")) {
                            map.put("hotelAmenities", h.getHotelAmenities());
                        }
                        if (cl.equalsIgnoreCase("HotelContactNo")) {
                            map.put("hotelContactNo", h.getHotelContactNo());
                        }
                        if (cl.equalsIgnoreCase("hotelCharges")) {
                            map.put("hotelCharges", h.getHotelCharges().toString());
                        }
                    }
                    return map;
                })
                .collect(Collectors.toList());
         System.out.println(hotelData);
        //return hotelData;
       return new PageDto(hotelPage.getContent(),hotelPage.getTotalPages(),hotelPage.getTotalElements(),hotelPage.getNumber());


    }
//        String[] HEADERS = {
//                "hotelName",
//                "hotelCity",
//                "hotelAddress",
//                "hotelAmenities",
//                "hotelContactNo",
//                "hotelCharges"
//        };
//        String SHEET_NAME = "hotel_data";

//    public ByteArrayInputStream getActualData(List<Map<String, String>> hotelData) {
//        Workbook workbook = new HSSFWorkbook();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        Sheet sheet = workbook.createSheet(SHEET_NAME);
//
//
//        Row headerRow = sheet.createRow(0);
//        for (int i = 0; i < HEADERS.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(HEADERS[i]);
//        }
//
//        int rowIndex = 1;
//        for (Map<String, String> map : hotelData) {
//            Row dataRow = sheet.createRow(rowIndex);
//            rowIndex++;
//
//            for (int i = 0; i < HEADERS.length; i++) {
//                String key = HEADERS[i];
//                String value = map.get(key);
//                Cell cell = dataRow.createCell(i);
//                cell.setCellValue(value != null ? value : "");
//            }
//        }
//
//        try {
//            workbook.write(out);
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to write data to excel");
//        }
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }

//        List<Hotel>all=hotelRepository.findAll();
//        ByteArrayInputStream byteArrayInputStream=this.getAllHotelByFilter(all);
//        return byteArrayInputStream;



      /*  if (hotelAddress != null && !hotelAddress.isEmpty()) {
            hotelList = hotelRepository.findByHotelAddressIn(hotelAddress);
        }
        if (hotelAmenities != null && !hotelAmenities.isEmpty()) {
            hotelList = hotelRepository.findByHotelAmenitiesIn(hotelAmenities);
        }
        if (hotelCharges != null && !hotelCharges.isEmpty()) {
            hotelList = hotelRepository.findByHotelChargesIn(hotelCharges);
        }
        if (hotelCity != null && !hotelCity.isEmpty()) {
            hotelList = hotelRepository.findByHotelCityIn(hotelCity);
        }
        if (hotelContactNo != null && !hotelContactNo.isEmpty()) {
            hotelList = hotelRepository.findByHotelContactNoIn(hotelContactNo);
        }
        if (hotelName != null && !hotelName.isEmpty()) {
            hotelList = hotelRepository.findByHotelNameIn(hotelName);
        }*/
//        if (hotelAddress != null && hotelAmenities != null && hotelCharges != null && hotelCity != null && hotelContactNo != null && hotelName != null) {
//            hotelList.addAll(hotelRepository.getAllHotelAddressAndHotelAmenitiesAndHotelChargesAndHotelCityAndHotelContactNoAndHotelName(hotelAddress, hotelAmenities, hotelCharges, hotelCity, hotelContactNo, hotelName));
//        }


//        if (hotelList.isEmpty()) {
//            return hotelRepository.findAll(pageable);
//        }
//        return hotelList;
//    }

//
//                List<Hotel> filteredHotels = new ArrayList<>(hotelList);
//                int start = (int) pageable.getOffset();
//                int end = Math.min((start + pageable.getPageSize()), filteredHotels.size());
//                List<Hotel> pagedHotels = filteredHotels.subList(start, end);
//
//                List<HotelResponse> hotelResponseList = new ArrayList<>();
//                for (Hotel hotel : pagedHotels) {
//                    HotelResponse hotelResponse = new HotelResponse();
//                    hotelResponse.setHotelId(hotel.getHotelId());
//                    hotelResponse.setHotelAddress(hotel.getHotelAddress());
//                    hotelResponse.setHotelAmenities(hotel.getHotelAmenities());
//                    hotelResponse.setHotelCharges(hotel.getHotelCharges());
//                    hotelResponse.setHotelCity(hotel.getHotelCity());
//                    hotelResponse.setHotelContactNo(hotel.getHotelContactNo());
//                    hotelResponse.setHotelName(hotel.getHotelName());
//                    hotelResponseList.add(hotelResponse);
//                }
//                Page<HotelResponse> hotelPage = new PageImpl<>(hotelResponseList, pageable, filteredHotels.size());
//        List<Map<String, String>> hotelData = hotelPage.stream()
//                .map(h -> {
//                    Map<String, String> map = new HashMap<>();
//                    map.put("hotelName", h.getHotelName());
//                    map.put("hotelCity", h.getHotelCity());
//                    map.put("hotelAddress", h.getHotelAddress());
//                    map.put("hotelAmenities", h.getHotelAmenities());
//                    map.put("hotelContactNo", h.getHotelContactNo());
//                    map.put("hotelCharges", h.getHotelCharges().toString());
//                    return map;
//                })
//                .collect(Collectors.toList());
//
//
//        System.out.println(hotelData);
//        return hotelData;
//                return new PageDto(hotelPage.getContent(), hotelPage.getTotalPages(), hotelPage.getTotalElements(), hotelPage.getNumber());
//    }

    @Override
    public Object saveOrUpdateAmenities(AmenitiesRequest amenitiesRequest) {
        if (amenitiesRepository.existsById(amenitiesRequest.getAmenityId())){
            Amenities amenities = amenitiesRepository.findById(amenitiesRequest.getAmenityId()).get();
            saveOrUpdateAmenities(amenities,amenitiesRequest);
            return "update successfully";

        }else {
            Amenities amenities = new Amenities();
            saveOrUpdateAmenities(amenities,amenitiesRequest);
            return "save data successfully";
        }
    }

    @Override
    public Object getAllHotel() {
        List<Hotel> hotels =hotelRepository.findAll();

        return hotels;
    }

    @Override
    public Object exportdataToExcel(HttpServletResponse response) throws IOException {
        List<Hotel> list = hotelRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hotel Data");
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("hotelId");
        headerRow.createCell(1).setCellValue("hotelName");
        headerRow.createCell(2).setCellValue("hotelAddress");
        headerRow.createCell(3).setCellValue("hotelCity");
        headerRow.createCell(4).setCellValue("hotelContactNo");
        headerRow.createCell(5).setCellValue("hotelCharges");
        headerRow.createCell(6).setCellValue("hotelAmenities");
        int rowNum = 1;
        for(Hotel hotel:list)
        {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hotel.getHotelId());
            row.createCell(1).setCellValue(hotel.getHotelName());
            // String contactString = String.format("%.0f", excel.getContact());
            row.createCell(2).setCellValue(hotel.getHotelAddress());
            row.createCell(3).setCellValue(hotel.getHotelCity());
            row.createCell(4).setCellValue(hotel.getHotelContactNo());
            row.createCell(5).setCellValue(hotel.getHotelCharges());
            row.createCell(6).setCellValue(hotel.getHotelAmenities());
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return "excel sheet downloaded";
    }


    public void saveOrUpdateAmenities(Amenities amenities,AmenitiesRequest amenitiesRequest){
        amenities.setHotelId(amenitiesRequest.getHotelId());
        amenities.setAmenityName(amenitiesRequest.getAmenityName());
        amenities.setIsActive(true);
        amenities.setIsDeleted(false);
        amenitiesRepository.save(amenities);
    }

    public void saveOrUpdateHotel (Hotel hotel, HotelRequest hotelRequest){
            hotel.setHotelName(hotelRequest.getHotelName());
            hotel.setHotelCity(hotelRequest.getHotelCity());
            hotel.setHotelAddress(hotelRequest.getHotelAddress());
            hotel.setHotelCharges(hotelRequest.getHotelCharges());
            hotel.setHotelAmenities(hotelRequest.getHotelAmenities());
            hotel.setHotelContactNo(hotelRequest.getHotelContactNo());
            hotel.setIsActive(true);
            hotelRepository.save(hotel);
            if (hotelRequest.getAmenityIds() != null && !hotelRequest.getAmenityIds().isEmpty()) {
            List<Long> validAmenityIds = hotelRequest.getAmenityIds().stream()
                    .filter(id -> id != 0)
                    .collect(Collectors.toList());
            if (!validAmenityIds.isEmpty()) {
                saveHotelAmenities(validAmenityIds, hotel.getHotelId());
            }
        }
        }

        public void saveHotelAmenities(List<Long>amenityIds,Long hotelId){
        if (hotelAmenitiesRepository.existsByHotelId(hotelId)){

            hotelAmenitiesRepository.deleteAllByHotelId(hotelId);
        }
            for(Long ai:amenityIds){
                HotelAmenities hotelAmenities = new HotelAmenities();
                hotelAmenities.setAmenityId(ai);
                hotelAmenities.setHotelId(hotelId);
                hotelAmenitiesRepository.save(hotelAmenities);
            }
        }

    public Object save(MultipartFile file) {
        try {

            List<Hotel> hotels = this.convertExcelToListOfHotel(file.getInputStream());
            System.out.println("file= "+file.getOriginalFilename());
            System.out.println("hotels = "+hotels);
            hotelRepository.saveAll(hotels);

            return hotels;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hotel> getAllHotell() {
        return this.hotelRepository.findAll();

    }

    public static List<Hotel> convertExcelToListOfHotel(InputStream is) throws Exception {

        if (is != null) {
            List<Hotel> list = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            int rowNumber = 0;

            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    // Skip the header row
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.cellIterator();
                Hotel h = new Hotel();

                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            if (h.getHotelName() == null) {
                                h.setHotelName(cell.getStringCellValue());
                            }else if (h.getHotelAddress() == null) {
                                h.setHotelAddress(cell.getStringCellValue());
                            }else if (h.getHotelCity() == null){
                                h.setHotelCity(cell.getStringCellValue());
                            }else if (h.getHotelAmenities() == null){
                                h.setHotelAmenities(cell.getStringCellValue());
                            }
                            break;
                        case NUMERIC:
                            if (h.getHotelId() == null) {
                                h.setHotelId((long) cell.getNumericCellValue());
                            } else if (h.getHotelCharges() == null) {
                                h.setHotelCharges((Double) cell.getNumericCellValue());
                            }else if (h.getHotelContactNo() == null){
                                h.setHotelContactNo((String.valueOf(cell.getNumericCellValue())));
                            }
                            break;
                        // Add handling for other cell types if necessary
                        default:
                            // Handle other cell types as needed
                    }
                }
                // Add the product to the list after processing all cells in the row
                list.add(h);
            }
            return list;
        }else {
            throw new Exception(" file is not found !!! ");
        }
    }

    public static String[]HEADERS={
            "hotelId",
            "hotelName",
            "hotelAddress",
            "hotelCity",
            "hotelContactNo",
            "hotelCharges",
            "hotelAmenities",
    };

    public static String SHEET_NAME="product_data";


    public static ByteArrayInputStream dataToExcel(List<Hotel>list) throws IOException {
        Workbook workbook=new XSSFWorkbook();
        ByteArrayOutputStream out=new ByteArrayOutputStream();

        try{

            Sheet sheet = workbook.createSheet(SHEET_NAME);

            Row row = sheet.createRow(0);

            for (int i=0; i<HEADERS.length; i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }
            int rowIndex=1;
            for (Hotel h:list){
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(h.getHotelId());
                dataRow.createCell(1).setCellValue(h.getHotelName());
                dataRow.createCell(2).setCellValue(h.getHotelAddress());
                dataRow.createCell(3).setCellValue(h.getHotelCity());
                dataRow.createCell(4).setCellValue(h.getHotelContactNo());
                dataRow.createCell(5).setCellValue(h.getHotelCharges());
                dataRow.createCell(6).setCellValue(h.getHotelAmenities());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("fail to import data excel");
        }
        finally {
            workbook.close();
            out.close();
        }
        return null;
    }

    public ByteArrayInputStream getActualData() throws IOException {
        List<Hotel>all=hotelRepository.findAll();
        ByteArrayInputStream byteArrayInputStream=this.dataToExcel(all);
        return byteArrayInputStream;
    }

}
