package com.booking.recruitment.hotel.repository;

import com.booking.recruitment.hotel.model.Hotel;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    String HAVERSINE_DISTANCE =
            "6371 * acos(cos(radians(:latitude)) * cos(radians(h.latitude)) * cos(radians(h.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(h.latitude)))";

    @Query("select h from Hotel h WHERE h.city.id = :cityId AND h.deleted = false ORDER BY " + HAVERSINE_DISTANCE + " ASC")
    List<Hotel> getHotelsCloseToCityCenter(
            Long cityId, double latitude, double longitude, PageRequest pageRequest);
}