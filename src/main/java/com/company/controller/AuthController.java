package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    @Lazy
    private AuthService authService;



    @ApiOperation(value = "Registration", notes="Method for registration")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid ProfileDTO dto) {
        return ResponseEntity.ok().body(authService.register(dto));
    }

    @ApiOperation(value = "Login", notes="Method for login")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody @Valid AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
