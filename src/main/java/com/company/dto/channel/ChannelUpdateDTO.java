package com.company.dto.channel;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChannelUpdateDTO {
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 1,max = 255,message = "Name's length should be between 1 and 255 characters")
    private String name;

    @NotEmpty(message = "Telegram url shouldn't be empty")
    @Size(min = 1,max = 500,message = "Telegram url length should be between 1 and 500 characters")
    private String telegramUrl;

    @NotEmpty(message = "Instagram url shouldn't be empty")
    @Size(min = 1,max = 500,message = "Instagram url length should be between 1 and 500 characters")
    private String instagramUrl;

    @NotEmpty(message = "Website url shouldn't be empty")
    @Size(min = 1,max = 500,message = "Website url length should be between 1 and 500 characters")
    private String websiteUrl;
}
