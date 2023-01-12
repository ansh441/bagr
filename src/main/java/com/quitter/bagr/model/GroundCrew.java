package com.quitter.bagr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "bagr.ground_crew")
public class GroundCrew {
    @Id
    private int id;
    private String status;
    private int flight_id;
}
