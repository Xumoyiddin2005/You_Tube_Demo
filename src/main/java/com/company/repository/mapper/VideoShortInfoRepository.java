package com.company.repository.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfoRepository {
    String getVideoId();
    String getVideoName();
    String getPreviewId();
    LocalDateTime getCreatedDate();

    String getChannelId();
    String getChannelName();
    String getPhotoId();

    Integer getViewCount();
}
