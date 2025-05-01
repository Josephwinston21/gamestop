package com.gamestop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name = "games")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NonNull
    @Size(min = 3, max = 225, message = "The name of the game must be minimum 3 letters and maximum 225 letters")
    private String name;

    @Column(name = "genre")
    private String genre;

    @Column(name = "mrp")
    @Min(value = 1, message = "MRP cannot be negative")
    private int mrp;

    @Column(name = "rating")
    @DecimalMin(value = "1.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot be greater than 10")
    private double rating;

    public Game(){}

}
