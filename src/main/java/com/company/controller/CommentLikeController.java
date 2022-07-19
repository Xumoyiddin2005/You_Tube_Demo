package com.company.controller;

import com.company.dto.comment.CommentLikeDTO;
import com.company.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody CommentLikeDTO dto
    ) {

        commentLikeService.commentLike(dto.getCommentId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody CommentLikeDTO dto) {
        commentLikeService.commentDisLike(dto.getCommentId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CommentLikeDTO dto) {
        commentLikeService.removeLike(dto.getCommentId());
        return ResponseEntity.ok().build();
    }

}
