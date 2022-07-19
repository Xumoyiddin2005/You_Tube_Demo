package com.company.controller;

import com.company.dto.video.VideoDTO;
import com.company.dto.video.VideoShortInfoDTO;
import com.company.dto.video.VideoStatusDTO;
import com.company.dto.video.VideoUpdateDTO;
import com.company.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Controller
@RequestMapping("api/v1/video")
public class VideoController {
    @Autowired
    private  VideoService videoService;

    @ApiOperation(value = "Create", notes="Method for creating a video")
    @PostMapping("/public/create")
    public ResponseEntity<String> create(@RequestBody @Valid VideoDTO dto) {
        return ResponseEntity.ok(videoService.create(dto));
    }

    @ApiOperation(value = "Update", notes="Method for updating a video")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id,
                                         @RequestBody @Valid VideoUpdateDTO dto) {
        return ResponseEntity.ok(videoService.update(dto,id));
    }

    @ApiOperation(value = "Change status", notes="Method for changing a video's status")
    @PutMapping("/public/change/status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id,
                                         @RequestBody @Valid VideoStatusDTO dto) {
        return ResponseEntity.ok(videoService.changeStatus(dto,id));
    }

    @ApiOperation(value = "increase view", notes="Method for increasing video's view count")
    @PutMapping("/public/increase/view_count/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id) {
        return ResponseEntity.ok(videoService.increaseViewCount(id));
    }

    @ApiOperation(value = "pagination_by_category", notes="Method for pagination_by_category")
    @GetMapping("/public/pagination_by_category/{id}")
    public ResponseEntity<List<VideoShortInfoDTO>> paginationByCategory(@PathVariable("id") Integer id,
                                                                        @RequestParam("size") int size,
                                                                        @RequestParam("page") int page) {
        return ResponseEntity.ok(videoService.paginationByCategory(id,size,page));
    }

    @ApiOperation(value = "getByName", notes="Method for getting a video by name")
    @GetMapping("/public/get_by_name")
    public ResponseEntity<VideoShortInfoDTO> getByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(videoService.getByName(name));
    }


}
