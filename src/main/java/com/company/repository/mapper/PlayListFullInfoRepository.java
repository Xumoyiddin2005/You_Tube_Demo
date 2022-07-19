package com.company.repository.mapper;

import com.company.enums.PlaylistStatus;

import javax.persistence.criteria.CriteriaBuilder;

public interface PlayListFullInfoRepository {
    String getPeId();
    String getPeName();
    PlaylistStatus getPeStatus();
    Integer getPeOrder();

    String getCId();
    String getCName();
    String getCPhotoId();

    Integer getPId();
    String getPUsername();
    String getPPhotoId();


}
