package com.company.service;

import com.company.entity.ProfileEntity;
import com.company.entity.video.VideoEntity;
import com.company.entity.video.VideoLikeEntity;
import com.company.enums.LikeStatus;
import com.company.enums.VideoLikeType;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.VideoLikeRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// PROJECT NAME -> you_tobe_demo
// TIME -> 22:12
// MONTH -> 07
// DAY -> 10
@Service
public class VideoLikeService {

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private VideoRepository videoRepository;

    public void articleLike(String articleId) {
        likeDislike(articleId, profileService.getProfile().getId(), VideoLikeType.LIKE);
    }

    public void articleDisLike(String articleId) {
        likeDislike(articleId,  profileService.getProfile().getId(), VideoLikeType.DISLIKE);
    }

    private void likeDislike(String videoId, Integer pId, VideoLikeType status) {
        Optional<VideoLikeEntity> optional = videoLikeRepository.findExists(videoId, pId);
        if (optional.isPresent()) {
            VideoLikeEntity like = optional.get();
            like.setType(status);
            videoLikeRepository.save(like);
            return;
        }
        boolean articleExists = videoRepository.existsById(videoId);
        if (!articleExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        VideoLikeEntity like = new VideoLikeEntity();
        like.setVideo(new VideoEntity(videoId));
        like.setProfile(new ProfileEntity(pId));
        like.setType(status);
        videoLikeRepository.save(like);
    }

    public void removeLike(String articleId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        videoLikeRepository.delete(articleId,  profileService.getProfile().getId());
    }


}
