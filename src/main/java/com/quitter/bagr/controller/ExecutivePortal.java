package com.quitter.bagr.controller;

import com.quitter.bagr.core.bagrException;
import com.quitter.bagr.helper.DashboardHelper;
import com.quitter.bagr.model.Executive;
import com.quitter.bagr.model.Itinerary;
import com.quitter.bagr.repository.PassengerRepo;
import com.quitter.bagr.services.ExecutiveService;
import com.quitter.bagr.services.ItineraryService;
import com.quitter.bagr.view.ApiResponse;
import com.quitter.bagr.view.Status;
import com.quitter.bagr.view.dashboard.ItineraryResponse;
import com.quitter.bagr.view.dashboard.loginResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/executivePortal")
public class ExecutivePortal {
    @Autowired
    private ExecutiveService service;
    ItineraryService itineraryService;
    PassengerRepo passengerRepo;
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

    @PostMapping("/addExecutive")
    public Executive addExecutive(@RequestBody Executive executive){

        return service.saveExecutive(executive);
    }
    @GetMapping("/getExecutiveById")
    public Executive getExecutive(@RequestBody int executiveId) {
        return service.getExecutive(executiveId);

    }


}

