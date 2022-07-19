package com.company.controller;

import com.company.dto.playlist.*;
import com.company.service.PlaylistService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/playlist")
public class PlaylistController {

    @Autowired
    @Lazy
    private PlaylistService playlistService;

    // 1
    @ApiOperation(value = "Create", notes = "Method for creating a playlist")
    @PostMapping("/public/create")
    public ResponseEntity<String> create(@RequestBody @Valid PlaylistDTO dto) {
        return ResponseEntity.ok(playlistService.create(dto));
    }

    // 2
    @ApiOperation(value = "Update", notes = "Method for updating a playlist")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id,
                                         @RequestBody @Valid PlaylistDTO dto) {
        return ResponseEntity.ok(playlistService.update(dto, id));
    }

    // 3
    @ApiOperation(value = "Update", notes = "Method for updating a playlist")
    @PutMapping("/public/change/status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id,
                                               @RequestBody @Valid PlaylistStatusDTO dto) {
        return ResponseEntity.ok(playlistService.changeStatus(dto, id));
    }

    // 4
    @ApiOperation(value = "Delete", notes = "Method for deleting a playlist")
    @PutMapping("/public/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(playlistService.delete(id));
    }

    // 5
    @ApiOperation(value = "Pagination",
            notes = "Get playlist pagination with full info")
    @GetMapping("/adm/pagination")
    public ResponseEntity<List<PlayListFullInfoDTO>> pagination(@RequestParam("size") int size,
                                                                @RequestParam("page") int page) {
        return ResponseEntity.ok(playlistService.pagination(size, page));
    }

    // 6
    @ApiOperation(value = "Get playlist list by user id",
            notes = "Get playlist list by user id for user with full info")
    @GetMapping("/adm/list/{id}")
    private ResponseEntity<List<PlayListFullInfoDTO>> listWithFullInfo(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(playlistService.listWithFullInfo(id));
    }

    // 7
    @ApiOperation(value = "Get playlist list by user id",
            notes = "Get playlist list by user id for user with short info")
    @GetMapping("/public/list/{id}")
    public ResponseEntity<List<PlayListShortInfoDTO>> listWithShortInfo(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(playlistService.listWithShortInfo(id));
    }


    // 8
    @ApiOperation(value = "Get Playlist list by channel",
            notes = "Get Playlist list by channel id with full info")
    @GetMapping("/public/list_by_channel/{id}")
    public ResponseEntity<List<PlayListFullInfoDTO>> getPlaylistListByChannel(@PathVariable("id") String id) {
        return ResponseEntity.ok(playlistService.listByChannel(id));
    }

    // 9
    @ApiOperation(value = "Get Playlist",
            notes = "Get Playlist by id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomPlaylistDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(playlistService.getById(id));
    }


}
