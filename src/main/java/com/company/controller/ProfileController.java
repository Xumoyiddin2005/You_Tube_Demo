package com.company.controller;


import com.company.config.CustomUserDetails;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileEmailDTO;
import com.company.dto.profile.ProfilePasswordDTO;
import com.company.dto.profile.ProfileUsernameDTO;
import com.company.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @PutMapping("/change/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ProfilePasswordDTO dto) {
        return ResponseEntity.ok(profileService.changePassword(dto,getCurrentUser()));
    }

    @PutMapping("/change/username")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ProfileUsernameDTO dto) {
        return ResponseEntity.ok(profileService.changeUsername(dto,getCurrentUser()));
    }

    @PutMapping("/change/email")
    public ResponseEntity<String> changeEmail(@RequestBody @Valid ProfileEmailDTO dto) {
        return ResponseEntity.ok(profileService.changeEmail(dto,getCurrentUser()));
    }

    @GetMapping("/get_details")
    public ResponseEntity<ProfileDTO> getDetails() {
        return ResponseEntity.ok(profileService.getDetails(getCurrentUser()));
    }

    @PostMapping("/adm/create")
    private ResponseEntity<String> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }
}
