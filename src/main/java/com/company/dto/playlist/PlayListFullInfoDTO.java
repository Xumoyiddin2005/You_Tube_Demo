package com.company.dto.playlist;

import com.company.dto.channel.ChannelDTO;
import com.company.dto.profile.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListFullInfoDTO {
    private PlaylistDTO playlist;
    private ChannelDTO channel;
    private ProfileDTO profile;
}
