package com.online.market.authentificationservice.controllers;

import com.online.market.authentificationservice.entries.AuthRequest;
import com.online.market.authentificationservice.entries.AuthResponse;
import com.online.market.authentificationservice.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody final AuthRequest authRequest) {
        try{
            return ResponseEntity.ok(authService.login(authRequest));
        }
        catch(AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");


    }
}
