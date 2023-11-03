package com.foodapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ig;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user_ratings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rating_id")

    )
    private List<Rating> ratings = new ArrayList<>();
    private Integer extraPoints;
}
