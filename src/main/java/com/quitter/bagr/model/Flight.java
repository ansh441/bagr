package com.quitter.bagr.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue
    int id;
    private String src_airport;
    private String dest_airport;
    private Date arrival_time;
    private Date departure_time;
    private String gate;
    private String is_loaded;
    private String status;
    private String belt;
    private String total_baggage;
}
