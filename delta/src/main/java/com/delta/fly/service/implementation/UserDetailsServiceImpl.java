package com.delta.fly.service.implementation;

import com.delta.fly.model.User;
import com.delta.fly.model.UserPrinciple;
import com.delta.fly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import java.util.List;
//import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found -> username : " + username));
//        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream().
//                map(authority -> new SimpleGrantedAuthority(authority.getName().getName())).
//                collect(Collectors.toList());
        return UserPrinciple.build(user);
    }

}
