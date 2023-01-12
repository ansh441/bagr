package com.quitter.bagr.model;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bagr.passenger")
public class Passenger {
    @Id
    @GeneratedValue
    private int id;
    private int age;
    private String first_name;
    private String  last_name;
    private String pnr;
    private int flight_id;
    private boolean is_checked_in;
    private String metadata;
    private int itinerary_id;
}
