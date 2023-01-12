package com.quitter.bagr.controller;

import com.quitter.bagr.core.bagrException;
import com.quitter.bagr.helper.DashboardHelper;
import com.quitter.bagr.model.Executive;
import com.quitter.bagr.services.ExecutiveService;
import com.quitter.bagr.view.ApiResponse;
import com.quitter.bagr.view.Status;
import com.quitter.bagr.view.dashboard.loginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/executivePortal")
public class ExecutivePortal {
    @Autowired
    private ExecutiveService service;
    private final String AUTHORIZATION = "Authorization";


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
            String token = "key";

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

    @PostMapping("/AddExecutive")
    public Executive addExecutive(@RequestBody Executive executive){

        return service.saveExecutive(executive);
    }
    @GetMapping("/getExecutiveById")
    public Executive getExecutive(@RequestBody int executiveId)
    {
        return service.getExecutive(executiveId);

    }
}

