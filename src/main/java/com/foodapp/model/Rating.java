package com.foodapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer value;
    private String description;
    private LocalDate dateAdded;
    @ManyToMany(mappedBy = "ratings")
    private List<User> users = new ArrayList<>();
    @ManyToMany(mappedBy = "ratings")
    private List<Restaurant> restaurants = new ArrayList<>();
}
