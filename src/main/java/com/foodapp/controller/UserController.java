package com.foodapp.controller;

import com.foodapp.model.address.AddressDTO;
import com.foodapp.model.address.AddressService;
import com.foodapp.model.user.User;
import com.foodapp.model.user.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
public class UserController {
    private final UserService userService;
    private final AddressService addressService;

    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping("/profile")
    public String profileForm(Principal principal, Model model) {
        Integer points = getExtraPointsOfAuthenticatedUser(principal);
        model.addAttribute("points", points);
        return "user-profile/profile";
    }

    @PostMapping("/change-address")
    public String changeAddress(@ModelAttribute("addressDTO") AddressDTO addressDTO, Principal principal){

        try {
            String name = principal.getName();
            addressService.addAddressForUser(name, addressDTO);
        }catch (UsernameNotFoundException e){
            return "redirect:/address-error";
        }
        return "redirect:/confirmation-address";
    }

    @GetMapping("confirmation-address")
    public String addressConfirmation(){
        return "user-profile/change-address-confirmation";
    }

    @PostMapping("/change-password")
    public String changePassword(Principal principal,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword){
        try {
            String name = principal.getName();
            userService.changeUserPassword(name, currentPassword,newPassword);
        } catch (IllegalArgumentException | UsernameNotFoundException e){
            return "redirect:/password-change-error";
        }
        return "redirect:/confirmation-password";
    }

    @GetMapping("/confirmation-password")
    public String passwordConfirmation(){
        return "user-profile/change-password-confirmation";
    }

    private Integer getExtraPointsOfAuthenticatedUser(Principal principal){
        String username = principal.getName();
        User user = userService.findUser(username).orElseThrow(NoSuchElementException::new);
        return user.getExtraPoints();
    }
}
