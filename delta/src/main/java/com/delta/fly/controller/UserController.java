package com.delta.fly.controller;

import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompanyAdmin;
import com.delta.fly.model.Passenger;
import com.delta.fly.model.SystemAdmin;
import com.delta.fly.model.User;
import com.delta.fly.repository.UserRepository;
import com.delta.fly.service.abstraction.AirlineCompanyAdminService;
import com.delta.fly.service.abstraction.PassengerService;
import com.delta.fly.service.abstraction.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AirlineCompanyAdminService airlineCompanyAdminService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private SystemAdminService systemAdminService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signup/{type}")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO request, @PathVariable String type) throws InvalidInputException, ObjectNotFoundException {
        Optional<User> user;
        switch (type) {
            case "system-admin":
                user = Optional.ofNullable(systemAdminService.create(request));
                break;
            case "airline-company-admin":
                user = Optional.ofNullable(airlineCompanyAdminService.create(request, 0L));
                break;
            case "passenger":
                user = Optional.ofNullable(passengerService.create(request));
                break;
            default:
                return new ResponseEntity<>("Invalid link!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User " + user.get().getUsername() + " registered successfully! Check your email address to verify your account.", HttpStatus.OK);
    }

    @GetMapping("/validate/token={token}")
    public ResponseEntity<?> activate(@PathVariable String token) throws InvalidInputException, ObjectNotFoundException {
        boolean success = passengerService.activate(token);
        if (success) {
            return new ResponseEntity<>("Account activated! Enjoy our website!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong. Please try clicking the link, or register again.", HttpStatus.BAD_REQUEST);
        }
    }

}
