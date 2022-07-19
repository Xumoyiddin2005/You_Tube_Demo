package com.company.dto.playlist;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomPlaylistDTO {
    private String playlistId;
    private String playlistName;
    private LocalDateTime playlistUpdatedDate;

    private Integer videoCount;
    private Integer totalViewCount;

}
