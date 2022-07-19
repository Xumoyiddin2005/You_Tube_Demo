package com.company.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProfileUsernameDTO {
    @NotEmpty(message = "Username shouldn't be empty!")
    @Size(min = 1,max = 255,message = "Username length should be between 1 and 255 characters!")
    private String username;
}
