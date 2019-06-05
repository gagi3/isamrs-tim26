package com.delta.fly.service.implementation;

import com.delta.fly.exception.ObjectNotFoundException;
import com.delta.fly.model.AirlineCompanyAdmin;
import com.delta.fly.model.Passenger;
import com.delta.fly.model.User;
import com.delta.fly.model.UserPrinciple;
import com.delta.fly.repository.UserRepository;
import com.delta.fly.service.abstraction.AirlineCompanyAdminService;
import com.delta.fly.service.abstraction.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AirlineCompanyAdminService airlineCompanyAdminService;

    @Autowired
    private PassengerService passengerService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found -> username : " + username));
        return UserPrinciple.build(user);
    }

    public String getUsername() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == String.class) {
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public AirlineCompanyAdmin getAdmin() throws ObjectNotFoundException {
        return airlineCompanyAdminService.getByUsername(getUsername());
    }

    public Passenger getPassenger() throws ObjectNotFoundException {
        return passengerService.getByUsername(getUsername());
    }

}
