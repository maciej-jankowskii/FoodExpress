package com.foodapp.model.address;

import com.foodapp.model.user.User;
import com.foodapp.model.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AddressServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private AddressMapper addressMapper;
    @Mock private AddressRepository addressRepository;
    @InjectMocks private AddressService addressService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should add address without exceptions")
    public void addAddressForUser_Success_Test(){
        AddressDTO dto = new AddressDTO();
        Address address = new Address();
        User user = new User();
        String email = "test@example.com";
        user.setEmail(email);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(addressMapper.map(dto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(userRepository.save(user)).thenReturn(user);
        when(addressMapper.map(address)).thenReturn(dto);
        AddressDTO result = addressService.addAddressForUser(email, dto);

        assertNotNull(result);
        assertEquals(dto, result);
        assertDoesNotThrow(() -> addressService.addAddressForUser(email, dto));

    }

    @Test
    @DisplayName("When a address is added, an exception should be thrown")
    public void addAddressForUser_Exception_Test(){
        AddressDTO addressDTO = new AddressDTO();
        String email = "test@example.com";

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> addressService.addAddressForUser(email, addressDTO));
    }

}