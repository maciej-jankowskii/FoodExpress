package com.foodapp.model.user;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDTO map(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setExtraPoints(user.getExtraPoints());
        Set<String> roles = user.getRoles().stream().map(UserRole::getName).collect(Collectors.toSet());
        dto.setRoles(roles);
        return dto;
    }

    public User map(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setExtraPoints(userDTO.getExtraPoints());
        return user;
    }
}
