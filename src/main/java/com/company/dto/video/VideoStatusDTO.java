package com.company.dto.video;

import com.company.enums.VideoStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VideoStatusDTO {
    @NotNull(message = "Status shouldn't be empty!")
    private VideoStatus status;
}
