package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthDTO {
    @NotEmpty(message = "Email shouldn't be empty")
    @Size(min = 6,max = 255,message = "Email length should be between 6 and 255 characters")
    private String email;

    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 1,max = 16,message = "Password length should be between 6 and 16 characters")
    private String password;
}
