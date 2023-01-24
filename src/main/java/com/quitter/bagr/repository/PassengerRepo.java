package com.quitter.bagr.repository;

import com.quitter.bagr.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger,Integer> {
    @Query(value = "SELECT p FROM Passenger p WHERE p.pnr = ?1")
    Passenger getByPnr(String pnr);
}
