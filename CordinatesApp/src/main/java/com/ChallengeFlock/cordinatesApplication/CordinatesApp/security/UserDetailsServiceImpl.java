package com.ChallengeFlock.cordinatesApplication.CordinatesApp.security;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.User;
import com.ChallengeFlock.cordinatesApplication.CordinatesApp.repository.UserRepository;
import com.ChallengeFlock.cordinatesApplication.CordinatesApp.service.CordinatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CordinatesService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("Logged user was not found");
            logger.error("A UsernameNotFoundException was thrown");
            throw new UsernameNotFoundException(username);
        }
        return new CustomerUserDetails(user);
    }
}
