package com.company.dto.category;

import com.company.enums.CategoryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CategoryDTO {
    private Integer id;
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 1,max = 255,message = "Name's length should be between 1 and 255 characters")
    private String name;
    private CategoryStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
}
