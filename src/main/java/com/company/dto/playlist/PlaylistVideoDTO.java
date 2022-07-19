package com.company.dto.playlist;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoDTO {
    private String id;

    @NotEmpty(message = "Video shouldn't be empty")
    private String videoId;

    @NotEmpty(message = "Playlist shouldn't be empty")
    private String playlistId;

    @NotEmpty(message = "Channel shouldn't be empty")
    private String channelId;

    @NotNull(message = "Order shouldn't be empty")
    private Integer order;



    private LocalDateTime createdDate;
}
