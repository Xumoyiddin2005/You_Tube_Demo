package com.company.dto.playlist;

import com.company.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {
    private String id;

    @NotEmpty(message = "Name shouldn't be empty!")
    @Size(min = 1,max = 255,message = "Name's length should be between 1 and 255 characters!")
    private String name;

    private String channelId;

    @NotNull(message = "Order shouldn't be empty")
    private Integer order;

    private PlaylistStatus status ;
    private Boolean visible;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @NotEmpty(message = "Preview shouldn't be empty")
    private String previewId;
}
