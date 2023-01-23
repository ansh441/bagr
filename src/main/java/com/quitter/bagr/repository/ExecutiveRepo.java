package com.quitter.bagr.repository;

import com.quitter.bagr.model.Executive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutiveRepo extends JpaRepository<Executive, Integer> {
    @Query(value = "SELECT e FROM Executive e WHERE e.username = ?1")
    Executive findByUsername(String username);
}
