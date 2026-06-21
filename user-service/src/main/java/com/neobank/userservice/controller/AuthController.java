package com.neobank.userservice.controller;

import com.neobank.userservice.dto.RegisterRequest;
import com.neobank.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.neobank.userservice.dto.RegisterResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegisterResponse register(
        @RequestBody RegisterRequest request) {

    return userService.register(request);
    }
    
}
