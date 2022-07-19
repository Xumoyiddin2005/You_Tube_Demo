package com.company.controller;

import com.company.service.AuthService;
import com.company.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/v1/email")
public class EmailController {
    @Autowired
    private  AuthService authService;

    @ApiOperation(value = "Email verification", notes="Method for email verification")
    @GetMapping("/verification/{token}")
    public ResponseEntity<String> login(@PathVariable String token) {
        Integer id = JwtUtil.decode(token);
        return ResponseEntity.ok(authService.emailVerification(id));
    }
}
