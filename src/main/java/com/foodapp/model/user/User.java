package com.foodapp.model.user;

import com.foodapp.model.address.Address;
import com.foodapp.model.order.Order;
import com.foodapp.model.rating.Rating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user_ratings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rating_id")

    )
    private List<Rating> ratings = new ArrayList<>();
    private Double extraPoints;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<UserRole> roles = new HashSet<>();
}
