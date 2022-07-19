package com.company.repository;

import com.company.entity.ChannelEntity;
import com.company.enums.ChannelStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity,String> {
    boolean existsByPhotoIdOrBannerId(String photoId, String bannerId);

    boolean existsByIdAndVisible(String id,Boolean visible);

    Optional<ChannelEntity> findByIdAndVisible(String id,Boolean visible);


    @Modifying
    @Transactional
    @Query("update ChannelEntity set photoId=:photoId where id=:id")
    void updatePhoto(@Param("photoId") String photoId,
                     @Param("id") String id);

    @Modifying
    @Transactional
    @Query("update ChannelEntity set bannerId=:bannerId where id=:id")
    void updateBanner(@Param("bannerId") String bannerId,
                      @Param("id") String id);

    @Modifying
    @Transactional
    @Query("update ChannelEntity set status=:status where id=:id")
    void updateStatus(@Param("status") ChannelStatus status,
                      @Param("id") String id);

    List<ChannelEntity> getAllByProfileId(Integer profileId);
}
