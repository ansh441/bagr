package com.quitter.bagr.controller;

import com.quitter.bagr.core.bagrException;
import com.quitter.bagr.helper.DashboardHelper;
import com.quitter.bagr.model.Executive;
import com.quitter.bagr.model.Flight;
import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.model.Passenger;
import com.quitter.bagr.repository.FlightRepo;
import com.quitter.bagr.repository.ItineraryRepo;
import com.quitter.bagr.repository.PassengerRepo;
import com.quitter.bagr.services.ExecutiveService;
import com.quitter.bagr.services.ItineraryService;
import com.quitter.bagr.view.ApiResponse;
import com.quitter.bagr.view.Status;
import com.quitter.bagr.view.dashboard.ItineraryResponse;
import com.quitter.bagr.view.dashboard.loginResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Portal/executive")
public class ExecutivePortal {
    private final ExecutiveService service;
    final ItineraryService itineraryService;
    final PassengerRepo passengerRepo;
    final ItineraryRepo itineraryRepo;
    final FlightRepo flightRepo;

    public ExecutivePortal(ExecutiveService service, ItineraryService itineraryService,
                           PassengerRepo passengerRepo, ItineraryRepo itineraryRepo,FlightRepo flightRepo) {
        this.service = service;
        this.itineraryService = itineraryService;
        this.passengerRepo = passengerRepo;
        this.itineraryRepo = itineraryRepo;
        this.flightRepo = flightRepo;
    }

    private final String AUTHORIZATION = "Authorization";
    @Value("${spring.auth.secret}")
    private String secretKey;


    @PostMapping("/login")
    private ApiResponse<loginResponse> login(@RequestHeader(AUTHORIZATION) String authString){
        ApiResponse.ApiResponseBuilder<loginResponse> responseBuilder = ApiResponse.builder();
        try{
            //get the user
            String[] decoded = DashboardHelper.decodeBasicAuth(authString);
            if(decoded == null)
            {
                throw new bagrException(400,"Invalid Authorization provided", bagrException.Reason.BAD_REQUEST);
            }
            String userName = decoded[0];
            Executive found = service.findByUsername(userName);
            if(found==null)
            {
                throw new bagrException(404, "Username not found", bagrException.Reason.NOT_FOUND);
            }
            //verify password
            String password = decoded[1];
            if(!DashboardHelper.isPasswordCorrect(password,found.getHashed_password()))
            {
                throw new bagrException(401,"Not Authorised", bagrException.Reason.NOT_AUTHORISED);

            }
            //get the key for the user
            Map<String,String> claimsMap = new HashMap<>();
            claimsMap.put("username",userName);
            claimsMap.put("exec_Id",String.valueOf(found.getId()));
            claimsMap.put("name", found.getName());

            String token = DashboardHelper.generateToken(found.getUsername(),secretKey,claimsMap);

            //respond with the key
            responseBuilder
                    .payload(loginResponse.builder().token(token).build())
                    .status(new Status());
        }
        catch (bagrException e)
        {
            responseBuilder.status(e.toStatus());
        }
        return responseBuilder.build();
    }

    @PutMapping("/itinerary")
    public ApiResponse<ItineraryResponse> changeItinerary(@RequestHeader(AUTHORIZATION) String bearerToken, @RequestBody Itinerary updatedItinerary,@RequestParam String pnr){

        ApiResponse.ApiResponseBuilder<ItineraryResponse> responseBuilder = ApiResponse.builder();
        // verify Token
        try{
            Claims claims = DashboardHelper.getClaims(bearerToken,secretKey);
            // Identify caller
            String userName = claims.get("username").toString();

            // Execute
            String message = String.format("Hi %s",userName);
            itineraryService.updateItineraryById(updatedItinerary,passengerRepo.getByPnr(pnr));
            // Respond

            responseBuilder.payload(ItineraryResponse.builder()
                            .message(message)
                            .build()).status(new Status());

        }catch(Exception e){
            responseBuilder.status(Status.builder()
                    .code(300).message(e.getMessage())
                    .reason(bagrException.Reason.INTERNAL_SERVER_ERROR.toString())
                    .build());

        }


        return responseBuilder.build();
    }

    @PutMapping("/checkin")
    public ApiResponse<ItineraryResponse> PassengerCheckin(@RequestHeader(AUTHORIZATION) String bearerToken, @RequestParam String passengerPNR){
        ApiResponse.ApiResponseBuilder<ItineraryResponse> responseBuilder = ApiResponse.builder();
        try {
            //get the passenger
            Passenger passenger = passengerRepo.getByPnr(passengerPNR);
            //get the executive
            Claims claims = DashboardHelper.getClaims(bearerToken, secretKey);
            String userName = claims.get("username").toString();

            String message = String.format("Passenger %s is checked-in by %s",
                    passenger.getFirst_name(), userName);
            Executive executive = service.findByUsername(userName);
            //update the executive details
            executive.setTotal_checkins((Integer.parseInt(executive.getTotal_checkins()) + 1) + "");
            Itinerary iti = itineraryRepo.getItineraryByPassengerId(passenger.getId());
            executive.setTotal_baggage(executive.getTotal_baggage()+ iti.getCheckin_luggage_qty());
            service.saveExecutive(executive);
            //check-in the passenger
            passenger.set_checked_in(true);
            passengerRepo.save(passenger);
            //flight details updated
            Flight flight;
            Optional<Flight> optionalFlight= flightRepo.findById(passenger.getFlight_id());
            if(optionalFlight.isPresent())
                flight=optionalFlight.get();
            else {
                responseBuilder.status(Status.builder().code(404)
                        .message("flight of the corresponding passenger is Not found ")
                        .reason(bagrException.Reason.NOT_FOUND.toString()).build());
                return responseBuilder.build();
            }
            flight.setTotal_baggage(String.valueOf(Integer.parseInt(flight.getTotal_baggage())
                    + iti.getCheckin_luggage_qty()));
            flightRepo.save(flight);
            responseBuilder.payload(ItineraryResponse.builder()
                    .message(message).build()).status(new Status());



        }
        catch(Exception e){
            responseBuilder.status(Status.builder()
                    .code(300).message(e.getMessage())
                    .reason(bagrException.Reason.INTERNAL_SERVER_ERROR.toString()).build());
        }
        return responseBuilder.build();
    }

    @PostMapping("/Executive/new")
    public Executive addExecutive(@RequestBody Executive executive){

        return service.saveExecutive(executive);

    }
    @GetMapping("/executive")
    public Executive getExecutive(@RequestBody int executiveId) {
        return service.getExecutive(executiveId);

    }


}

