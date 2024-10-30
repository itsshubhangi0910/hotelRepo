package com.example.hotel_details_project.repository;

import com.example.hotel_details_project.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    @Query(value = "SELECT * FROM hotel_db WHERE hotel_address IN (:hotelAddress)", nativeQuery = true)
    List<Hotel> findByHotelAddressIn(List<String> hotelAddress);

    @Query(value = "SELECT * FROM `hotel_db` WHERE hotel_amenities IN (:hotelAmenities)",nativeQuery = true)
    List<Hotel> findByHotelAmenitiesIn(@Param("hotelAmenities")List<String> hotelAmenities);

    @Query(value = "SELECT * FROM `hotel_db` WHERE hotel_charges IN (:hotelCharges)",nativeQuery = true)
    List<Hotel> findByHotelChargesIn(@Param("hotelCharges")List<Double> hotelCharges);

    @Query(value = "SELECT * FROM `hotel_db` WHERE hotel_city IN (:hotelCity)",nativeQuery = true)
    List<Hotel> findByHotelCityIn(@Param("hotelCity")List<String> hotelCity);

    @Query(value = "SELECT * FROM `hotel_db` WHERE hotel_contact_no IN (:hotelContactNo)",nativeQuery = true)
    List<Hotel> findByHotelContactNoIn(@Param("hotelContactNo")List<String> hotelContactNo);

    @Query(value = "SELECT * FROM `hotel_db` WHERE hotel_name IN (:hotelName)",nativeQuery = true)
    List<Hotel> findByHotelNameIn(List<String> hotelName);

    @Query(value = "SELECT h.hotel_address AS hotelAddress, " +
            "       h.hotel_amenities AS hotelAmenities, " +
            "       h.hotel_charges AS hotelCharges, " +
            "       h.hotel_city AS hotelCity, " +
            "       h.hotel_contact_no AS hotelContactNo, " +
            "       h.hotel_name AS hotelName " +
            "FROM hotel_db h " +
            "WHERE h.hotel_address IN :hotelAddress AND " +
            "      h.hotel_amenities IN :hotelAmenities AND " +
            "      h.hotel_charges IN :hotelCharges AND " +
            "      h.hotel_city IN :hotelCity AND " +
            "      h.hotel_contact_no IN :hotelContactNo AND " +
            "      h.hotel_name IN :hotelName",
            nativeQuery = true)
    List<Hotel> getAllHotelAddressAndHotelAmenitiesAndHotelChargesAndHotelCityAndHotelContactNoAndHotelName(
            @Param("hotelAddress") List<String> hotelAddress,
            @Param("hotelAmenities") List<String> hotelAmenities,
            @Param("hotelCharges") List<Double> hotelCharges,
            @Param("hotelCity") List<String> hotelCity,
            @Param("hotelContactNo") List<String> hotelContactNo,
            @Param("hotelName") List<String> hotelName
    );


        @Query(value = "SELECT h FROM Hotel as h WHERE \n" +
                "(COALESCE(:hotelAddress, '')='' OR TRIM(h.hotelAddress) IN (:hotelAddress)) and \n" +
                "(COALESCE(:hotelAmenities, '')='' OR TRIM(h.hotelAmenities) IN (:hotelAmenities)) and \n" +
                "(COALESCE(:hotelCharges, 0.0)=0.0 OR TRIM(h.hotelCharges) IN (:hotelCharges)) and \n" +
                "(COALESCE(:hotelCity, '')='' OR TRIM(h.hotelCity) IN (:hotelCity)) and \n" +
                "(COALESCE(:hotelContactNo, '')='' OR TRIM(h.hotelContactNo) IN (:hotelContactNo)) and \n" +
                "(COALESCE(:hotelName, '')='' OR TRIM(h.hotelName) IN (:hotelName))",nativeQuery = false)
        Page<Hotel> searchAllHotelFilter(
                @Param("hotelAddress") List<String> hotelAddress,
                @Param("hotelAmenities") List<String> hotelAmenities,
                @Param("hotelCharges") List<Double> hotelCharges,
                @Param("hotelCity") List<String> hotelCity,
                @Param("hotelContactNo") List<String> hotelContactNo,
                @Param("hotelName") List<String> hotelName,
                Pageable pageable);
    //    @Query("SELECT h FROM Hotel h WHERE "
//            + "(:address IS NULL OR LOWER(h.hotelAddress) IN :address) "
//            + "AND (:stDate IS NULL OR :edDate IS NULL OR h.checkInDate BETWEEN :stDate AND :edDate) "
//            + "AND (:hotelCharges IS NULL OR h.hotelCharges <= :hotelCharges) "
//            + "AND (:hotelCity IS NULL OR LOWER(h.hotelCity) = LOWER(:hotelCity)) "
//            + "AND (:hotelName IS NULL OR LOWER(h.hotelName) = LOWER(:hotelName)) "
//            + "AND (:hotelAmenities IS NULL OR h.hotelAmenities IN :hotelAmenities)")
//    Page<Hotel> searchHotelByFilterWithDates(@Param("address") List<String> address, @Param("stDate") LocalDate stDate, @Param("edDate") LocalDate edDate, @Param("hotelCharges") Double hotelCharges, @Param("hotelCity") String hotelCity, @Param("hotelName") String hotelName, @Param("hotelAmenities") List<String> hotelAmenities, Pageable pageable);
//
//    @Query("SELECT h FROM Hotel h WHERE "
//            + "(:address IS NULL OR LOWER(h.hotelAddress) IN :address) "
//            + "AND (:hotelCharges IS NULL OR h.hotelCharges <= :hotelCharges) "
//            + "AND (:hotelCity IS NULL OR LOWER(h.hotelCity) = LOWER(:hotelCity)) "
//            + "AND (:hotelName IS NULL OR LOWER(h.hotelName) = LOWER(:hotelName)) "
//            + "AND (:hotelAmenities IS NULL OR LOWER(h.hotelAmenities) IN :hotelAmenities)")
//    Page<Hotel> searchHotelByFilterWithOutDates(@Param("address") List<String> address, @Param("hotelCharges") Double hotelCharges, @Param("hotelCity") String hotelCity, @Param("hotelName") String hotelName, @Param("hotelAmenities") List<String> hotelAmenities, Pageable pageable);

//    @Query(value ="SELECT h FROM Hotel h WHERE " +
//            "(:hotelAddress IS NULL OR LOWER(h.hotelAddress) IN (:hotelAddress)) AND " +
//            "(:hotelContactNo IS NULL OR h.hotelContactNo IN (:hotelContactNo)) AND " +
//            "(:hotelCharges IS NULL OR h.hotelCharges IN (:hotelCharges)) AND " +
//            "(:hotelCity IS NULL OR LOWER(h.hotelCity) IN (:hotelCity)) AND " +
//            "(:hotelName IS NULL OR LOWER(h.hotelName) IN (:hotelName)) AND " +
//            "(:hotelAmenities IS NULL OR LOWER(h.hotelAmenities) IN (:hotelAmenities))",nativeQuery = false)
//    Page<Hotel> searchAllHotelByFilter(List<String> hotelAddress, List<String> hotelContactNo, List<Double> hotelCharges, List<String> hotelCity, List<String> hotelName, List<String> hotelAmenities,Pageable pageable);

//    Page<Hotel> searchAllHotelByFilterWithAllData(@Param("address") List<String> hotelAddress,
//                                    @Param("hotelContactNo") List<String> hotelContactNo,
//                                    @Param("hotelCharges") List<Double> hotelCharges,
//                                    @Param("hotelCity") List<String> hotelCity,
//                                    @Param("hotelName") List<String> hotelName,
//                                    @Param("hotelAmenities") List<String> hotelAmenities,
//                                    Pageable pageable);


}

