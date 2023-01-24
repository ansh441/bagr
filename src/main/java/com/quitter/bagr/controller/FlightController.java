package com.quitter.bagr.controller;

import com.quitter.bagr.model.Flight;
import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(path = "/demo")
public class FlightController {
    @Autowired
    private FlightService flightService;
    @PostMapping("/AddFlight")
    public Flight addFlight(@RequestBody Flight flight){
        return flightService.saveFlight(flight);
    }
    @PostMapping("/AddFlights")
    public List<Flight> addFlights(@RequestBody List<Flight> flights)
    {
        return flightService.saveFlights(flights);
    }

}
