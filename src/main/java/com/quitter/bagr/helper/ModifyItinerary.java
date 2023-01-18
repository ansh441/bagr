package com.quitter.bagr.helper;

import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.model.Passenger;
import com.quitter.bagr.repository.PassengerRepo;
import com.quitter.bagr.services.ItineraryService;


public class ModifyItinerary {
    static PassengerRepo passengerRepo;
    static ItineraryService itineraryService;
    public static Itinerary updateItinerary(Itinerary updatedItinerary, String pnr){
        //get passenger by pnr
        Passenger passenger = passengerRepo.getByPnr(pnr);
        //get the passenger's itinerary id
        int itinerary_id = passenger.getItinerary_id();
        //get the itinerary using id and update it
        return itineraryService.updateItineraryById(updatedItinerary,itinerary_id);




    }
}
