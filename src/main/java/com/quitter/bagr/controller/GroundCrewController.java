package com.quitter.bagr.controller;

import com.quitter.bagr.core.bagrException;
import com.quitter.bagr.model.Flight;
import com.quitter.bagr.model.GroundCrew;
import com.quitter.bagr.repository.FlightRepo;
import com.quitter.bagr.repository.GroundCrewRepo;
import com.quitter.bagr.repository.PassengerRepo;
import com.quitter.bagr.view.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Portal/groundCrew")
public class GroundCrewController {

    private final FlightRepo flightRepo;
    private final GroundCrewRepo groundCrewRepo;
    private final PassengerRepo passengerRepo;

    public GroundCrewController(FlightRepo flightRepo, GroundCrewRepo groundCrewRepo,
                                PassengerRepo passengerRepo) {
        this.flightRepo = flightRepo;
        this.groundCrewRepo = groundCrewRepo;
        this.passengerRepo = passengerRepo;
    }

    @GetMapping("/gate-to-belt")
    public List<Flight> gateToBelt(@RequestParam int crewId )throws bagrException{
        //gate-flight, belt-airport
        //gateToBelt - loaded
        //beltToGate - unloaded
            GroundCrew groundCrew = groundCrewRepo.getReferenceById(crewId);
            int flightId = groundCrew.getFlight_id();
            Optional<Flight> optionalFlight = flightRepo.findById(flightId);
            List<Flight> list;
            if (optionalFlight.isPresent())
                list = optionalFlight.stream().toList();
            else
                throw new bagrException(404, "Flight is not found",
                        bagrException.Reason.NOT_FOUND);

            for(int i = 0; i<list.size(); i++){
                Flight flight = list.get(i);
                if(flight.getIs_loaded().equalsIgnoreCase("unloaded")){
                    groundCrew.setStatus("Belt to Gate move");
                    flight.setStatus("Departing Flight");
//                    int totalBaggage = passengerRepo.getTotalLuggageFromItinerary(flightId);

                }
                else if(flight.getIs_loaded().equalsIgnoreCase("loaded")){
                    groundCrew.setStatus("Gate to Belt move");
                    flight.setStatus("Arrived Flight");

                }
                else{
                    throw new bagrException(300,"Invalid Information",
                            bagrException.Reason.INTERNAL_SERVER_ERROR);
                }
                groundCrewRepo.save(groundCrew);
                flightRepo.save(flight);
            }
        return list;

    }



}
