package com.quitter.bagr.services;

import com.quitter.bagr.model.Passenger;
import com.quitter.bagr.repository.PassengerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    @Autowired
    PassengerRepo passengerRepo;
    public Passenger savePassenger(Passenger passenger){
        return passengerRepo.save(passenger);
    }
    public Passenger getPassengerByPNR(String Ppnr){
        return passengerRepo.getByPnr(Ppnr);
    }
    public void savePassengerItineraryID(int id){

    }



}
