package com.booking.recruitment.hotel.fixtures;

import com.booking.recruitment.hotel.model.Hotel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class HotelDataFixtures {
  public List<Hotel> getHotelsWithRatingValues(Double... ratings) {
    return Stream.of(ratings)
        .map(rating -> Hotel.builder().setRating(rating).build())
        .collect(Collectors.toList());
  }
}
