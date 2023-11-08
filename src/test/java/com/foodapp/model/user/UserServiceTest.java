package com.foodapp.model.user;

import com.foodapp.exception.EmailAlreadyExistsException;
import com.foodapp.model.address.Address;
import com.foodapp.model.address.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AddressRepository addressRepository;
    @InjectMocks private UserService userService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find User by his e-mail address")
    public void findUserByEmail_Success_Test(){
        String email = "test@example.com";
        User user = new User();
        UserDTO dto = new UserDTO();

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.map(user)).thenReturn(dto);
        Optional<UserDTO> result = userService.findUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(result.get().getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Should register a new User")
    public void register_Success_Test(){
        String email = "test@example.com";
        String hashedPassword = "hashedPassword";
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setFirstName("Test");
        dto.setLastName("Test");
        dto.setEmail(email);
        dto.setPassword("password");
        User user = new User();
        Address address = new Address();



        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(hashedPassword);
        when(addressRepository.save(address)).thenReturn(address);
        when(userRepository.save(user)).thenReturn(user);
        userService.register(dto);

        verify(userRepository, times(1)).save(argThat(user1 -> {
            return user1.getFirstName().equals(dto.getFirstName()) &&
                    user1.getLastName().equals(dto.getLastName()) &&
                    user1.getEmail().equals(dto.getEmail()) &&
                    user1.getPassword().equals(hashedPassword);
        }));

    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException for existing email address")
    public void register_Exception_Throw_Test(){
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail("test@example.com");

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(dto));
    }

    @Test
    @DisplayName("Should change user password successfully")
    public void changeUserPassword_Success_Test(){
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "password")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");

        userService.changeUserPassword(user.getEmail(), user.getPassword(), "hashedNewPassword");

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException for non-existing user")
    public void changeUserPassword_UserNotFound_Exception_Test(){
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.changeUserPassword("test@example.com", "pass", "newPass"));

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for current password")
    public void changeUserPassword_IllegalArgument_Exception_Test(){
        String username = "testuser@example.com";
        String currentPassword = "currentPassword";
        String newPassword = "newPassword";

        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode("differentPassword")); // Różne hasło

        when(userRepository.findUserByEmail(username)).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.changeUserPassword(username, currentPassword, newPassword));

    }

}