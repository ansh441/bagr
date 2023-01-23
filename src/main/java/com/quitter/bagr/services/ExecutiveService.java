package com.quitter.bagr.services;

import com.quitter.bagr.model.Executive;
import com.quitter.bagr.repository.ExecutiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutiveService  {
    @Autowired
    private ExecutiveRepo executiveRepo;
    public Executive saveExecutive(Executive executive){
        return executiveRepo.save(executive);
    }
    public Executive getExecutive(int executiveId){
        return executiveRepo.getById(executiveId);
    }
    public Executive findByUsername(String executiveUsername){
        return executiveRepo.findByUsername(executiveUsername);
    }
}
