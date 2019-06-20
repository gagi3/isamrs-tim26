package com.delta.fly.controller;

import com.delta.fly.dto.JwtResponse;
import com.delta.fly.dto.LoginDTO;
import com.delta.fly.dto.RegisterDTO;
import com.delta.fly.exception.InvalidInputException;
import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.User;
import com.delta.fly.security.TokenUtils;
import com.delta.fly.service.abstraction.AirlineCompanyAdminService;
import com.delta.fly.service.abstraction.PassengerService;
import com.delta.fly.service.abstraction.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AirlineCompanyAdminService airlineCompanyAdminService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private SystemAdminService systemAdminService;

    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtils.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup/{type}")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDTO request, @PathVariable String type) throws InvalidInputException, ObjectNotFoundException {
        Optional<User> user;
        switch (type) {
            case "system-admin":
                user = Optional.ofNullable(systemAdminService.create(request));
                break;
            case "airline-company-admin":
                user = Optional.ofNullable(airlineCompanyAdminService.create(request));
                break;
            case "passenger":
                user = Optional.ofNullable(passengerService.create(request));
                break;
            default:
                return new ResponseEntity<>("Invalid link!", HttpStatus.BAD_REQUEST);
        }
        String s = "User registered successfully! Check your email address to verify your account.";
        return new ResponseEntity<>(s, HttpStatus.OK);
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
