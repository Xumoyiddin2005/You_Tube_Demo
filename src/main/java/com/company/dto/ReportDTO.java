package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.enums.EntityType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

public class ReportDTO {

    private String content;
    private String entityId;
    private EntityType type;
}
