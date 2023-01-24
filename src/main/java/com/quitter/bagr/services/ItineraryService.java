package com.quitter.bagr.services;

import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.model.Passenger;
import com.quitter.bagr.repository.ItineraryRepo;
import com.quitter.bagr.repository.PassengerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItineraryService {

    final ItineraryRepo itineraryRepo;
    final PassengerRepo passengerRepo;

    public ItineraryService(ItineraryRepo itineraryRepo, PassengerRepo passengerRepo) {
        this.itineraryRepo = itineraryRepo;
        this.passengerRepo = passengerRepo;
    }

    public Itinerary getItineraryRepo(int itinerary_id){
        return itineraryRepo.getById(itinerary_id);
    }
    public Itinerary updateItineraryById(Itinerary update, Passenger passenger){
        Itinerary itinerary = itineraryRepo.getItineraryByPassengerId(passenger.getId());
        itinerary.setCheckin_luggage_qty(update.getCheckin_luggage_qty());
        itinerary.setCheckin_luggage_weight(update.getCheckin_luggage_weight());
        itinerary.setCabin_luggage_weight(update.getCabin_luggage_weight());
        itinerary.setAdd_ons(update.getAdd_ons());
        itineraryRepo.save(itinerary);
        return itinerary;

    }
}
