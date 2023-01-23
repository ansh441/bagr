package com.quitter.bagr.view;

import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.model.Passenger;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerResponse {
    public Passenger passenger;
    public Itinerary itinerary;

}
