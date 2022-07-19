package com.company.repository.mapper;

import java.time.LocalDateTime;

public interface PlayListShortInfoRepository {
    String getPeId();
    String getPeName();
    LocalDateTime getPeCreatedDate();

    String getCId();
    String getCName();

    String getVId();
    String getVName();
    Integer getVCount();
}
