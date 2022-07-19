package com.company.dto.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortInfoDTO {
    private String videoId;
    private String videoName;
    private String videoPreviewId;
    private LocalDateTime videoCreatedDate;

    private String channelId;
    private String channelName;
    private String channelPhotoId;

    private Integer viewCount;


}
