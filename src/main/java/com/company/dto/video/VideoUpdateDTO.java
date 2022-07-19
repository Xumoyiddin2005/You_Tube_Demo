package com.company.dto.video;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class VideoUpdateDTO {
    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 1,max = 255,message = "Name's length should be between 1 and 255 characters!")
    private String name;

    @NotEmpty(message = "Description shouldn't be empty!")
    private String description;
}
