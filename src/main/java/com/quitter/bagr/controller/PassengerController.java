package com.quitter.bagr.controller;

import com.quitter.bagr.core.bagrException;
import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.model.Passenger;
import com.quitter.bagr.repository.ItineraryRepo;
import com.quitter.bagr.services.PassengerService;
import com.quitter.bagr.view.ApiResponse;
import com.quitter.bagr.view.PassengerResponse;
import com.quitter.bagr.view.Status;
import com.quitter.bagr.view.dashboard.ItineraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengerPortal")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private ItineraryRepo itineraryRepo;
    Passenger currPassenger;
    //login
    @PostMapping("/addPassenger")
    public ApiResponse<PassengerResponse> AddPassenger(@RequestBody Passenger passenger)
    {
        currPassenger = passenger;
        passengerService.savePassenger(passenger);
        ApiResponse.ApiResponseBuilder<PassengerResponse> responseBuilder = ApiResponse.builder();
        responseBuilder.payload(PassengerResponse.builder()
                .passenger(passenger).build())
                .status(Status.builder()
                        .message("Passenger Details Added!! Go Ahead with the Itinerary").build());
        return responseBuilder.build();
    }
    @PostMapping("/AddItinerary")
    public ApiResponse<PassengerResponse> AddItinerary(@RequestBody Itinerary itinerary){
        ApiResponse.ApiResponseBuilder<PassengerResponse> apiResponseBuilder = ApiResponse.builder();

        itineraryRepo.save(itinerary);
        apiResponseBuilder.payload(PassengerResponse.builder().passenger(currPassenger)
                .itinerary(itinerary).build()).status(Status.builder()
                .message("Itinerary Added").build());
        return apiResponseBuilder.build();

    }
    @PutMapping("/ModifyYourItinerary")
    public




}
