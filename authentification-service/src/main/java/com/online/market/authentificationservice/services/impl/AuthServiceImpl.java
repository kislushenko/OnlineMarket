package com.online.market.authentificationservice.services.impl;

import com.online.market.authentificationservice.entries.AuthRequest;
import com.online.market.authentificationservice.entries.AuthResponse;
import com.online.market.authentificationservice.entries.UserVO;
import com.online.market.authentificationservice.services.AuthService;
import com.online.market.authentificationservice.services.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest request) throws AuthenticationException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            final UserVO userVO = restTemplate.getForObject("http://user-service/users/" + request.getEmail(), UserVO.class);
            if (userVO != null) {
                String accessToken = jwtUtil.generate(userVO.getEmail(), userVO.getRole(), "ACCESS");
                String refreshToken = jwtUtil.generate(userVO.getEmail(), userVO.getRole(), "REFRESH");

                return new AuthResponse(accessToken, refreshToken);
            } else {
                throw new AuthenticationException("Invalid email or password");
            }
        }
        catch (BadCredentialsException exception) {
            throw new AuthenticationException("Invalid email or password");
        }

    }

}
