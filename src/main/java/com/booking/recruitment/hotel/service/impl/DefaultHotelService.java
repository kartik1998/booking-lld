package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.CityRepository;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;
  private final CityRepository cityRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository, CityRepository cityRepository) {
    this.hotelRepository = hotelRepository;
    this.cityRepository = cityRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    return hotelRepository.findAll();
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository
        .findAll()
        .stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public Hotel getHotelById(Long id) {
    Hotel hotel = hotelRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ElementNotFoundException(
                    String.format("Could not find hotel with ID = %s", id)));
    // don't show deleted hotels to users
    if (hotel.isDeleted()) {
      throw new ElementNotFoundException(String.format("hotel with ID = %s is deleted", id));
    }

    return hotel;
  }

  @Override
  public void deleteHotelById(Long id) {
    Hotel hotel =
        hotelRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ElementNotFoundException(
                        String.format("Could not find hotel with ID = %s", id)));
    hotel.setDeleted(true);
    hotelRepository.save(hotel);
  }

  @Override
  public List<Hotel> getHotelsCloseToCityCenter(Long cityId) {
    City city =
        cityRepository
            .findById(cityId)
            .orElseThrow(
                () ->
                    new ElementNotFoundException(
                        String.format("Could not find city with ID = %s", cityId)));
    return hotelRepository.getHotelsCloseToCityCenter(
        city.getId(),
        city.getCityCentreLatitude(),
        city.getCityCentreLongitude(),
        PageRequest.of(0, 3));
  }
}
