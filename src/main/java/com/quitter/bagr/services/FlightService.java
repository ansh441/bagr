package com.quitter.bagr.services;

import com.quitter.bagr.model.Flight;
import com.quitter.bagr.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    private FlightRepo flightRepo;
    public Flight saveFlight(Flight flight){
        return flightRepo.save(flight);
    }
    public List<Flight> saveFlights(List<Flight> flights)
    {
        return flightRepo.saveAll(flights);
    }
    public List<Flight> getFlights(List<Flight> flights){
        return flightRepo.findAll();
    }

    public Optional<Flight> getFlightById(int flightId){
       return flightRepo.findById(flightId);

    }
}
