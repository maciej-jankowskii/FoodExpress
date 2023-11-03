package com.foodapp.model.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).map(userMapper::map);
    }

    public void register(UserRegistrationDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        String hashed = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashed);
        userRepository.save(user);
    }
}
