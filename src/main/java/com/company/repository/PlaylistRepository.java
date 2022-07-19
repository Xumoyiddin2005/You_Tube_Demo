package com.company.repository;

import com.company.entity.PlaylistEntity;
import com.company.enums.PlaylistStatus;
import com.company.repository.mapper.CustomPlaylistRepository;
import com.company.repository.mapper.PlayListFullInfoRepository;
import com.company.repository.mapper.PlayListShortInfoRepository;
import com.company.repository.mapper.VideoShortInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends PagingAndSortingRepository<PlaylistEntity,String> {

    // 7
    @Query(value = " select pe.id as peId,pe.name as peName, " +
            " pe.created_date as peCreateedDate, " +
            " c.id as cId,c.name as cName, " +
            " v.id as vId,v.name as vName," +
            " count(v.id) as vCount " +
            " from playlist_video pve " +
            " inner join playlist pe on pe.id=pve.playlist_id " +
            " inner join channel c on pe.channel_id=c.id " +
            " inner join video v on v.id = pve.video_id " +
            " inner join profile p on p.id = c.profile_id " +
            " where p.id = :id " +
            " group by pve.order_number," +
            " pe.name,pe.created_date, pe.name, pe.id, c.id, c.name, v.id, v.name" +
            " order by pve.order_number desc ;",nativeQuery = true)
    List<PlayListShortInfoRepository> findAllByProfileIdOrderByOrderNumberDesc(
            @Param("id") Integer id);

    @Query(value = "select pe.id as peId,pe.name as peName,pe.status as peStatus," +
            " pe.order_number as peOrder, c.id as cId,c.name as cName," +
            " c.photo_id as cPhotoId, " +
            " p.id as pId,p.username as pUsername," +
            " p.photo_id as pPhotoId " +
            " from playlist pe " +
            " inner join channel c " +
            " on pe.channel_id=c.id " +
            " inner join profile p " +
            " on p.id=c.profile_id " +
            " where p.id = :id" +
            " group by pe.order_number,pe.id,pe.name,pe.status," +
            " c.id,c.name,c.photo_id,p.id," +
            " p.photo_id,p.username" +
            " order by pe.order_number desc ;",nativeQuery = true)
    List<PlayListFullInfoRepository> findAllByProfileIdWithFullInfo(@Param("id") Integer id);


    @Modifying
    @Transactional
    @Query("update PlaylistEntity set status=?1 where id=?2")
    void changeStatus(PlaylistStatus status, String id);

    boolean existsByIdAndVisible(String id, Boolean visible);

    Optional<PlaylistEntity> findByIdAndVisible(String id, Boolean visible);

    @Modifying
    @Transactional
    @Query("update PlaylistEntity set visible=?1 where id=?2")
    void changeVisible(Boolean visible, String id);

    @Query(value = "select pe.id as peId,pe.name as peName,pe.status as peStatus," +
            " pe.order_number as peOrder, c.id as cId,c.name as cName," +
            " c.photo_id as cPhotoId, " +
            " p.id as pId,p.username as pUsername," +
            " p.photo_id as pPhotoId " +
            " from playlist pe " +
            " inner join channel c " +
            " on pe.channel_id=c.id " +
            " inner join profile p " +
            " on p.id=c.profile_id " +
            " group by pe.order_number,pe.id,pe.name,pe.status," +
            " c.id,c.name,c.photo_id,p.id," +
            " p.photo_id,p.username" +
            " order by pe.created_date asc " +
            " limit :limit " +
            " offset :offset ;",nativeQuery = true)
    List<PlayListFullInfoRepository>
    findAllByProfileIdWithFullInfoWithPagination(@Param("offset") int page,
                                                 @Param("limit") int size);

    @Query(value = " select pe.id as peId,pe.name as peName, " +
            " pe.created_date as peCreateedDate, " +
            " c.id as cId,c.name as cName, " +
            " v.id as vId,v.name as vName," +
            " p.photo_id as pPhotoId, " +
            " c.photo_id as cPhotoId " +
            " from playlist_video pve " +
            " inner join playlist pe on pe.id=pve.playlist_id " +
            " inner join channel c on pe.channel_id=c.id " +
            " inner join video v on v.id = pve.video_id " +
            " inner join profile p on p.id = c.profile_id " +
            " where c.id = :id " +
            " group by pve.order_number," +
            " pe.name,pe.created_date, pe.name, pe.id, c.id, c.name, v.id, v.name," +
            " c.photo_id, p.photo_id " +
            " order by pve.order_number desc ;",nativeQuery = true)
    List<PlayListFullInfoRepository> findAllByChannelIdWithShortInfo(@Param("id") String id);

    @Query("select pe.id as playlistId,pe.name as playlistName, " +
            " pe.updatedDate as playlistUpdatedDate, " +
            " count(pve.id) as videoCount, " +
            " sum(pve.video.viewCount) as totalViewCount " +
            " from PlaylistEntity pe " +
            " inner join PlaylistVideoEntity pve " +
            " on pe.id=pve.playlistId " +
            " where pe.id = :id " +
            " group by pe.id,pe.name,pe.updatedDate ")
    Optional<CustomPlaylistRepository> getById(@Param("id") String id);
}
