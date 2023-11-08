package com.foodapp.model.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    @Column(unique = true)
    @Email
    private String email;
    private String password;
    private String street;
    private String homeNo;
    private String flatNo;
    private String city;
    private String postalCode;
}
