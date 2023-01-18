package com.quitter.bagr.services;

import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.repository.ItineraryRepo;

public class ItineraryService {
    ItineraryRepo itineraryRepo;
    public Itinerary getItineraryRepo(int itinerary_id){
        return itineraryRepo.getById(itinerary_id);
    }
    public Itinerary updateItineraryById(Itinerary update, int id){
        Itinerary itinerary = itineraryRepo.getReferenceById(id);
        int  checkin_luggage_qty= update.getCheckin_luggage_qty();
        itinerary.setCheckin_luggage_qty(checkin_luggage_qty);
        itinerary.setCheckin_luggage_weight(update.getCheckin_luggage_weight());
        itinerary.setCabin_luggage_weight(update.getCabin_luggage_weight());
        itinerary.setAdd_ons(update.getAdd_ons());
        return itinerary;

    }
}
