package com.company.repository;
// PROJECT NAME -> you_tobe_demo
// TIME -> 22:12
// MONTH -> 07
// DAY -> 10

import com.company.entity.video.VideoLikeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, Integer> {


    @Query("FROM VideoLikeEntity a where  a.video.id=:articleId and a.profile.id =:profileId")
    Optional<VideoLikeEntity> findExists(String articleId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLikeEntity a where  a.video.id=:articleId and a.profile.id =:profileId")
    void delete(String articleId, Integer profileId);



}
