package com.online.market.userservice.controllers;

import com.online.market.userservice.dto.RegistrationRequest;
import com.online.market.userservice.dto.UserVO;
import com.online.market.userservice.services.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<Void> save(@RequestBody RegistrationRequest registrationRequest) {
        userService.register(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserVO> getUser(@PathVariable @NotBlank String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

}
