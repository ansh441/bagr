package com.quitter.bagr.repository;

import com.quitter.bagr.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItineraryRepo extends JpaRepository<Itinerary,Integer> {


}
