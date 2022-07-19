package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileEmailDTO;
import com.company.dto.profile.ProfilePasswordDTO;
import com.company.dto.profile.ProfileUsernameDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.ProfileAlreadyExists;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private  ProfileRepository profileRepository;

    @Autowired
    @Lazy
    private EmailService emailService;

    @Autowired
    @Lazy
    private AttachService attachService;


    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() ->
                new ProfileNotFoundException(
                        "Profile not found!"
                ));
    }

    public ProfileEntity get(String email) {
        return profileRepository.findByEmail(email).orElseThrow(() ->
                new ProfileNotFoundException(
                        "Profile not found!"
                ));
    }

    public String changePassword(ProfilePasswordDTO dto, CustomUserDetails user) {
        if (user.getPassword().equals(dto.getPassword())) {
            log.warn("Password wasn't changed");
            throw new BadRequestException(
                    "Password wasn't changed"
            );
        }
        profileRepository.updatePassword(MD5Util.getMd5(dto.getPassword()),user.getUsername());


        return "Successfully changed a password";
    }

    public String changeUsername(ProfileUsernameDTO dto, CustomUserDetails user) {
        profileRepository.updateUsername(dto.getUsername(),user.getUsername());

        return "Successfully changed a username";
    }

    public String changeEmail(ProfileEmailDTO dto, CustomUserDetails user) {
        if (profileRepository.existsByEmail(dto.getEmail())) {
            log.error("Email already taken");
            throw new ProfileAlreadyExists(
                    "Email already taken"
            );
        }

        if (user.getUsername().equals(dto.getEmail())) {
            log.warn("Email wasn't changed");
            throw new BadRequestException(
                    "Email wasn't changed"
            );
        }

        profileRepository.updateEmailAndStatus(
                dto.getEmail(),
                ProfileStatus.NON_ACTIVE,
                user.getUsername());

        emailService.resendRegistrationEmail(dto.getEmail());

        return "We've resent a verification link to your email account";
    }

    public ProfileDTO getDetails(CustomUserDetails currentUser) {
        Optional<ProfileEntity> optional =
                profileRepository.findByEmail(currentUser.getUsername());

        if (optional.isEmpty()) {
            log.error("Profile not found!");
            throw new ProfileNotFoundException(
                    "Profile not found!"
            );
        }

        ProfileEntity entity = optional.get();

        return toDTO(entity);
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPhotoUrl("TODO");

        return dto;
    }

    public String create(ProfileDTO dto) {
        if (profileRepository.existsByEmail(dto.getEmail())) {
            log.error("Profile already exists");
            throw new ProfileAlreadyExists(
                    "Profile already exists"
            );
        }

        ProfileEntity entity;

        if (dto.getPhotoId() == null) {
            entity = toEntityWithoutPhoto(dto);
        } else {
            entity = toEntityWithPhoto(dto);
        }

        profileRepository.save(entity);

        return "Succesfully saved a profile";

    }

    private ProfileEntity toEntityWithPhoto(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());

        if (!attachService.exists(dto.getPhotoId())) {
            log.error("Attach not found!");
            throw new ItemNotFoundException(
                    "Attach not found!"
            );
        }

        entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        return entity;
    }

    private ProfileEntity toEntityWithoutPhoto(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        entity.setRole(dto.getRole());
        entity.setVisible(dto.getVisible());

        return entity;
    }

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public ProfileEntity getProfile() {
        CustomUserDetails user = getCurrentUser();
        return user.getProfile();
    }

    public boolean existsPhoto(String id) {
        return profileRepository.existsByPhotoId(id);
    }

    public boolean exists(Integer profileId) {
        return profileRepository.existsById(profileId);
    }
}
