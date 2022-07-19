package com.company.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryVideoDTO {
    private Integer id;
    private String videoId;
    private Integer categoryId;
    private LocalDateTime createdDate;

}
