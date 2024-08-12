package com.online.market.authentificationservice.services;

import com.online.market.authentificationservice.entries.AuthRequest;
import com.online.market.authentificationservice.entries.AuthResponse;

import javax.naming.AuthenticationException;

public interface AuthService {

    AuthResponse login(AuthRequest request) throws AuthenticationException;

}
