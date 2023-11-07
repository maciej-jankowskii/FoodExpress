package com.foodapp.model.user;

import com.foodapp.model.address.Address;
import com.foodapp.model.address.AddressRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).map(userMapper::map);
    }

    @Transactional
    public void register(UserRegistrationDTO dto) {
        Address address = setInitialAddressData(dto);
        User user = setInitialUserData(dto, address);
        addressRepository.save(address);
        userRepository.save(user);
    }

    @Transactional
    public void changeUserPassword(String username, String currentPassword, String newPassword){
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(currentPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Wrong current password");
        }
    }


    /**
     Helper methods for find logged User and find his extra points
     */

    public User getLoggedInUser( ){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        return null;
    }

    public Double getExtraPointsOfAuthenticatedUser(){
        User loggedInUser = getLoggedInUser();
        String email = loggedInUser.getEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getExtraPoints();
    }


    /**
     Helper methods for register new User
     */

    private User setInitialUserData(UserRegistrationDTO dto, Address address) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAddress(address);
        String hashed = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashed);
        user.setExtraPoints(0.0);
        return user;
    }

    private Address setInitialAddressData(UserRegistrationDTO dto) {
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHomeNo(dto.getHomeNo());
        address.setFlatNo(dto.getFlatNo());
        address.setPostalCode(dto.getPostalCode());
        return address;
    }
}
