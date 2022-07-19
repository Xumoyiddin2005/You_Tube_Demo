package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.ProfileAlreadyExists;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    @Lazy
    private ProfileRepository profileRepository;

    @Autowired
    @Lazy
    private AttachService attachService;

    @Autowired
    @Lazy
    private EmailService emailService;


    public String register(ProfileDTO dto) {
        if (profileRepository.existsByEmail(dto.getEmail())) {
            log.error("Profile is already exists");
            throw new ProfileAlreadyExists(
                    "Profile is already exists"
            );
        }

        ProfileEntity entity;
        if (dto.getPhotoId() == null) {
            entity = toEntityWithoutPhoto(dto);
        } else {
            entity = toEntityWithPhoto(dto);
        }

        profileRepository.save(entity);

        String token = JwtUtil.encode(entity.getId(), ProfileRole.ROLE_USER);

        emailService.sendRegistrationEmail(
                entity.getEmail(),token);

        return "Message was sent";
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    public ProfileDTO login(AuthDTO authDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        CustomUserDetails user = (CustomUserDetails) authenticate.getPrincipal();
        ProfileEntity profile = user.getProfile();

        Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        if (!MD5Util.getMd5(authDTO.getPassword()).equals(profile.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No permission");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setUsername(profile.getUsername());
        dto.setEmail(profile.getEmail());
        dto.setJwt(JwtUtil.encode(profile.getId(), profile.getRole()));

        return dto;
    }

    public String emailVerification(Integer id) {
        if (!profileRepository.existsByIdAndStatus(id,ProfileStatus.NON_ACTIVE)) {
            log.error("Profile not found or already been verified!");
            throw new BadRequestException(
                    "Profile not found or already been verified!"
            );
        }

        profileRepository.updateStatus(ProfileStatus.ACTIVE,id);

        return "You've been successfully verified!";
    }

    private ProfileEntity toEntityWithPhoto(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setEmail(dto.getEmail());

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

        return entity;
    }
}
