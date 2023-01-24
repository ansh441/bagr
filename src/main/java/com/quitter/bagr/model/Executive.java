package com.quitter.bagr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "executive")
public class Executive {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String total_checkins;
    private int total_baggage;
    private String username;
    private String hashed_password;
}
