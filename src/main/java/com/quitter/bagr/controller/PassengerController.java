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
@RequestMapping("/passengerPortal")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private ItineraryRepo itineraryRepo;
    @Autowired
    private ItineraryService itineraryService;
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
//        currPassenger.setItinerary_id(itinerary.getId());
        itinerary.setPassenger_id(currPassenger.getId());
        apiResponseBuilder.payload(PassengerResponse.builder().passenger(currPassenger)
                .itinerary(itinerary).build()).status(Status.builder()
                .message("Itinerary Added").build());
        return apiResponseBuilder.build();

    }
    @PutMapping("/ModifyYourItinerary")
    public ApiResponse<PassengerResponse> ModifyItinerary(@RequestBody Itinerary updatedItinerary,
                                                          @RequestParam String pnr){
        ApiResponse.ApiResponseBuilder<PassengerResponse> responseBuilder = ApiResponse.builder();
//        try{
            Passenger passenger = passengerService.getPassengerByPNR(pnr);
//            if(passenger==null)
//            {
//                throw new bagrException(404, "Passenger not found", bagrException.Reason.NOT_FOUND);
//            }
            itineraryService.updateItineraryById(updatedItinerary,passenger);
            Itinerary finalItinerary = itineraryRepo. (passenger.getId());
            itineraryRepo.get(passenger.getId()),
            responseBuilder.payload(PassengerResponse.builder().passenger(passenger)
                    .itinerary(itineraryRepo.)
                    .build()).status(Status.builder()
                    .message("Hi" + passenger.getFirst_name() + "Your Itinerary changed!!").build());

//        }
//        catch(Exception e){
//            responseBuilder.status(Status.builder()
//                    .code(300).message(e.getMessage())
//                    .reason(bagrException.Reason.INTERNAL_SERVER_ERROR.toString()).build());
//        }
        return responseBuilder.build();
    }




}
