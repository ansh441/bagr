package com.quitter.bagr.repository;


import com.quitter.bagr.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepo extends JpaRepository<Flight,Integer> {
}
