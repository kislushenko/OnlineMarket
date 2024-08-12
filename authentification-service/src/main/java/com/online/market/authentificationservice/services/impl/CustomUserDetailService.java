package com.online.market.authentificationservice.services.impl;

import com.online.market.authentificationservice.entries.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserVO userVO = restTemplate.getForObject("http://user-service/users/" + email, UserVO.class);
        if (userVO == null) {
            throw new UsernameNotFoundException(email);
        }
        return User.withUsername(userVO.getEmail())
                .password(userVO.getPassword())
                .authorities("USER").build();
    }
}
