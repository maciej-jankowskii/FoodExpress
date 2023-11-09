package com.foodapp.model.address;

import org.springframework.stereotype.Service;

@Service
public class AddressMapper {
    public AddressDTO map(Address address){
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setCity(address.getCity());
        dto.setStreet(address.getStreet());
        dto.setHomeNo(address.getHomeNo());
        dto.setFlatNo(address.getFlatNo());
        dto.setPostalCode(address.getPostalCode());
        return dto;
    }

    public Address map(AddressDTO addressDTO){
        Address address = new Address();
        address.setId(address.getId());
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setHomeNo(addressDTO.getHomeNo());
        address.setFlatNo(addressDTO.getFlatNo());
        address.setPostalCode(addressDTO.getPostalCode());
        return address;
    }
}
