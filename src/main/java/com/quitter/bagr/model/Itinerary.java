package com.quitter.bagr.model;

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
@Table(name = "itinerary")
public class Itinerary {
    @Id
    @GeneratedValue
    private int id;
    private int checkin_luggage_qty =0;
    private int checkin_luggage_weight =0;
    private int cabin_luggage_weight =0;
    private String add_ons;
    private int passenger_id;

}
