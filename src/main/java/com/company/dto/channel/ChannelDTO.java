package com.company.dto.channel;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {
    private String id;
    private String name;
    private String photoId;
    private AttachDTO photo;
    private String bannerId;
    private Integer profileId;
    private String telegramUrl;
    private String instagramUrl;
    private String websiteUrl;
    private ChannelStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
}
