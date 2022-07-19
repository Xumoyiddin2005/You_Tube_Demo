package com.company.controller;
// PROJECT NAME -> You_Tube_Demo
// TIME -> 15:59
// MONTH -> 07
// DAY -> 13

import com.company.dto.video.VideoLikeDTO;
import com.company.service.VideoLikeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video_like")
public class VideoLikeController {

    @Autowired
    private VideoLikeService videoLikeService;


    @ApiOperation(value = " Like ", notes = "Method for Article Like")
    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody VideoLikeDTO dto) {
        videoLikeService.articleLike(dto.getVideoId());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = " Dislike ", notes = "Method for Article Dislike")
    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody VideoLikeDTO dto) {

        videoLikeService.articleDisLike(dto.getVideoId());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = " Remove ", notes = "Method for Article Remove")
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody VideoLikeDTO dto
    ) {
        videoLikeService.removeLike(dto.getVideoId());
        return ResponseEntity.ok().build();
    }

}

