package com.foodapp.model.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private String street;
    private String homeNo;
    private String flatNo;
    private String city;
    private String postalCode;
}
