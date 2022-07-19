package com.company.controller;

import com.company.dto.channel.ChannelAttachDTO;
import com.company.dto.channel.ChannelStatusDTO;
import com.company.dto.channel.ChannelCreateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.channel.ChannelUpdateDTO;
import com.company.service.ChannelService;
import com.company.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/channel")
public class ChannelController {
    @Autowired
    @Lazy
    private ChannelService channelService;
    @Autowired
    @Lazy
    private ProfileService profileService;


    @ApiOperation(value = "Create", notes="Method for creating a channel")
    @PostMapping("/public/create")
    public ResponseEntity<String> create(@RequestBody @Valid ChannelCreateDTO dto) {
        return ResponseEntity.ok(channelService.create(dto));
    }

    @ApiOperation(value = "Update", notes="Method for updating a channel")
    @PutMapping("/public/update/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid ChannelUpdateDTO dto,
                                         @PathVariable("id") String id) {
        return ResponseEntity.ok(channelService.update(dto,id));
    }

    @ApiOperation(value = "Update photo", notes="Method for updating a channel's photo")
    @PutMapping("/public/photo/update/{id}")
    public ResponseEntity<String> updatePhoto(@PathVariable("id") String id,
                                              @RequestBody @Valid ChannelAttachDTO dto) {
        return ResponseEntity.ok(channelService.updatePhoto(id,dto));
    }

    @ApiOperation(value = "Update banner", notes="Method for updating a channel's banner")
    @PutMapping("/public/banner/update/{id}")
    public ResponseEntity<String> updateBanner(@PathVariable("id") String id,
                                               @RequestBody @Valid ChannelAttachDTO dto) {
        return ResponseEntity.ok(channelService.updateBanner(id,dto));
    }

    @ApiOperation(value = "Pagination", notes="Method for channel pagination")
    @GetMapping("/adm/pagination")
    private ResponseEntity<List<ChannelDTO>> pagination(@RequestParam("size") int size,
                                                        @RequestParam("page") int page) {
        return ResponseEntity.ok(channelService.pagination(size,page));
    }

    @ApiOperation(value = "Get channel by id", notes="Method for getting a channel by it's id")
    @GetMapping("/{id}")
    private ResponseEntity<ChannelDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(channelService.getById(id));
    }

    @ApiOperation(value = "Get channel list by profile id",
            notes="Method for getting a channel list by profile id")
    @GetMapping("/list/{pId}")
    private ResponseEntity<List<ChannelDTO>> list(@PathVariable("pId") Integer id) {
        return ResponseEntity.ok(channelService.getListByProfileId(id));
    }

    @ApiOperation(value = "Get channel by id", notes="Method for getting a channel by it's id")
    @PutMapping("/change/status/{id}")
    private ResponseEntity<String> changeStatus(@PathVariable("id") String id,
                                                @RequestBody ChannelStatusDTO dto) {
        return ResponseEntity.ok(channelService.changeStatus(id,dto));
    }






}
