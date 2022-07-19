package com.company.service;

import com.company.entity.comment.CommentEntity;
import com.company.entity.comment.CommentLikeEntity;
import com.company.entity.ProfileEntity;

import com.company.enums.CommentLikeType;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CommentLikeRepository;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentlikeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    ProfileService profileService;

    public void commentLike(Integer commentId) {
        likeDislike(commentId, profileService.getProfile().getId(), CommentLikeType.LIKE);
    }

    public void commentDisLike(Integer commentId) {
        likeDislike(commentId, profileService.getProfile().getId(), CommentLikeType.DISLIKE);
    }

    private void likeDislike(Integer commentId, Integer pId, CommentLikeType type) {
        Optional<CommentLikeEntity> optional = commentlikeRepository.findExists(commentId, pId);
        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setType(type);
            commentlikeRepository.save(like);
            return;
        }
        boolean commentExists = commentRepository.existsById(commentId);
        if (!commentExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setComment( new CommentEntity(commentId));
        like.setProfile(new ProfileEntity(pId));
        like.setType(type);
        commentlikeRepository.save(like);
    }

    public void removeLike(Integer commentId) {
       /* Optional<ArticleLikeEntity> optional = commentlikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            commentlikeRepository.delete(articleLikeEntity);
        });*/
        commentlikeRepository.delete(commentId, profileService.getProfile().getId());
    }
}
