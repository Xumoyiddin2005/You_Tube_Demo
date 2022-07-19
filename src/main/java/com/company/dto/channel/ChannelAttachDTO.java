package com.company.dto.channel;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ChannelAttachDTO {
    @NotEmpty(message = "Attach shouldn't be empty")
    private String attachId;
}
