package com.quitter.bagr.controller;

import com.quitter.bagr.core.bagrException;
import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.model.Passenger;
import com.quitter.bagr.repository.ItineraryRepo;
import com.quitter.bagr.services.ItineraryService;
import com.quitter.bagr.services.PassengerService;
import com.quitter.bagr.view.ApiResponse;
import com.quitter.bagr.view.PassengerResponse;
import com.quitter.bagr.view.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Portal/passenger")
public class PassengerController {
    private final PassengerService passengerService;
    private final ItineraryRepo itineraryRepo;
    private final ItineraryService itineraryService;

    public PassengerController(PassengerService passengerService, ItineraryRepo itineraryRepo, ItineraryService itineraryService) {
        this.passengerService = passengerService;
        this.itineraryRepo = itineraryRepo;
        this.itineraryService = itineraryService;
    }

    Passenger currPassenger;
    //login
    @PostMapping("/new")
    public ApiResponse<PassengerResponse> AddPassenger(@RequestBody Passenger passenger) {
        currPassenger = passenger;
        passengerService.savePassenger(passenger);
        ApiResponse.ApiResponseBuilder<PassengerResponse> responseBuilder = ApiResponse.builder();
        responseBuilder.payload(PassengerResponse.builder()
                .passenger(passenger).build())
                .status(Status.builder()
                        .message("Passenger Details Added!! Go Ahead with the Itinerary").build());
        return responseBuilder.build();
    }

    @PostMapping("/Itinerary/new")
    public ApiResponse<PassengerResponse> AddItinerary(@RequestBody Itinerary itinerary){
        ApiResponse.ApiResponseBuilder<PassengerResponse> apiResponseBuilder = ApiResponse.builder();
        itinerary.setPassenger_id(currPassenger.getId());
        itineraryRepo.save(itinerary);
        apiResponseBuilder.payload(PassengerResponse.builder().passenger(currPassenger)
                .itinerary(itinerary).build()).status(Status.builder()
                .message("Itinerary Added").build());
        return apiResponseBuilder.build();

    }

    @PutMapping("/Itinerary/update")
    public ApiResponse<PassengerResponse> ModifyItinerary(@RequestBody Itinerary updatedItinerary,
                                                          @RequestParam String pnr){
        ApiResponse.ApiResponseBuilder<PassengerResponse> responseBuilder = ApiResponse.builder();
        try{
            Passenger passenger = passengerService.getPassengerByPNR(pnr);
            if(passenger==null)
            {
                throw new bagrException(404, "Passenger not found", bagrException.Reason.NOT_FOUND);
            }
            itineraryService.updateItineraryById(updatedItinerary,passenger);
            Itinerary finalItinerary = itineraryRepo.getItineraryByPassengerId(passenger.getId());
            responseBuilder.payload(PassengerResponse.builder().passenger(passenger)
                    .itinerary(finalItinerary)
                    .build()).status(Status.builder()
                    .message("Hi " + passenger.getFirst_name() + " Your Itinerary changed!!").build());

        }
        catch(Exception e){
            responseBuilder.status(Status.builder()
                    .code(300).message(e.getMessage())
                    .reason(bagrException.Reason.INTERNAL_SERVER_ERROR.toString()).build());
        }
        return responseBuilder.build();
    }




}
