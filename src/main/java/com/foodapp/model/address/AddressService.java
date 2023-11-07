package com.foodapp.model.address;

import com.foodapp.model.user.User;
import com.foodapp.model.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public AddressService(UserRepository userRepository, AddressMapper addressMapper, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public AddressDTO addAddressForUser(String email, AddressDTO addressDTO){
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Address address = createNewAddress(addressDTO);
        user.setAddress(address);
        addressRepository.save(address);
        userRepository.save(user);
        return addressMapper.map(address);
    }

    private Address createNewAddress(AddressDTO addressDTO){
        Address address = addressMapper.map(addressDTO);
        address.setCity(address.getCity());
        address.setStreet(address.getStreet());
        address.setHomeNo(address.getHomeNo());
        address.setFlatNo(address.getFlatNo());
        address.setPostalCode(address.getPostalCode());
        return address;
    }
}
