package com.company.service;

import com.company.dto.playlist.PlaylistVideoDTO;
import com.company.entity.video.PlaylistVideoEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.PlaylistVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlaylistVideoService {
    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ChannelService channelService;


    public String create(PlaylistVideoDTO dto) {
        if (!videoService.exists(dto.getVideoId())) {
            log.error("Video not found!");
            throw new ItemNotFoundException(
                    "Video not found!"
            );
        }

        if (!playlistService.exists(dto.getPlaylistId())) {
            log.error("Playlist not found!");
            throw new ItemNotFoundException(
                    "Playlist not found!"
            );
        }

        if (!channelService.exists(dto.getChannelId())) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }

        PlaylistVideoEntity entity = toEntity(dto);

        playlistVideoRepository.save(entity);

        return "Successfully saved a playlist video";
    }

    private PlaylistVideoEntity toEntity(PlaylistVideoDTO dto) {
        PlaylistVideoEntity entity = new PlaylistVideoEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setOrder(dto.getOrder());

        return entity;
    }
}
