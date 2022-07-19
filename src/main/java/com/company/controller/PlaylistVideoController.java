package com.company.controller;

import com.company.dto.playlist.PlaylistVideoDTO;
import com.company.service.PlaylistVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/playlist/video")
public class PlaylistVideoController {
    @Autowired
    @Lazy
    private PlaylistVideoService playlistVideoService;

    @ApiOperation(value = "Create", notes="Method for creating a playlist video")
    @PostMapping("/public/create")
    public ResponseEntity<String> create(@RequestBody @Valid PlaylistVideoDTO dto) {
        return ResponseEntity.ok(playlistVideoService.create(dto));

    }
}
