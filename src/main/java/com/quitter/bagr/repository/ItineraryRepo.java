package com.quitter.bagr.repository;

import com.quitter.bagr.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface ItineraryRepo extends JpaRepository<Itinerary,Integer> {
@Query(value = "SELECT i FROM Itinerary i WHERE i.passenger_id = ?1")
    public Itinerary getItineraryByPassengerId(int passneger_id);

}
