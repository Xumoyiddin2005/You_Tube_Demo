package com.company.dto;

import com.company.enums.TagStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Integer id;

    @NotBlank(message = "Tag name shouldn't be empty")
    @Size(min = 1,max = 255,message = "Name's length should be between 1 and 255 characters")
    private String name;

    private TagStatus status;

    private Boolean visible;

    private LocalDateTime createdDate;
}
