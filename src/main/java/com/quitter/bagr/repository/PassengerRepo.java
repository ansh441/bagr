package com.quitter.bagr.repository;

import com.quitter.bagr.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepo extends JpaRepository<Passenger,Integer> {
}
