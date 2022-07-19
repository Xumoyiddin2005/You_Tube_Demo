package com.company.dto.profile;

import com.company.dto.AttachDTO;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;


    @NotEmpty(message = "Username shouldn't be empty!")
    @Size(min = 1,max = 255,message = "Username length should be between 1 and 255 characters!")
    private String username;

    @NotEmpty(message = "Password shouldn't be empty!")
    @Size(min = 1,max = 16,message = "Password length should be between 1 and 16 characters!")
    private String password;

    @NotEmpty(message = "Email shouldn't be empty!")
    @Size(min = 1,max = 255,message = "Email length should be between 1 and 255 characters!")
    private String email;

    private ProfileStatus status;
    private ProfileRole role;
    private String photoId;
    private Boolean visible;
    private LocalDateTime createdDate;

    private String jwt;
    private String photoUrl;
    private AttachDTO photo;
}
