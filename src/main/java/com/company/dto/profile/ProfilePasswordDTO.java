package com.company.dto.profile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProfilePasswordDTO {
    @NotEmpty(message = "Password shouldn't be empty!")
    @Size(min = 1,max = 16,message = "Password length should be between 1 and 16 characters!")
    private String password;
}
