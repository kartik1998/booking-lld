package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

  @Autowired private HotelService hotelService;

  @GetMapping("/{cityId}")
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> searchHotelsInCity(
      @PathVariable Long cityId, @RequestParam(required = true) String sortBy) {
    switch (sortBy.toLowerCase()) {
      case "distance":
        return hotelService.getHotelsCloseToCityCenter(cityId);
      default:
        return hotelService.getAllHotels();
    }
  }
}
