package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.*;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.*;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoDTO;
import com.company.entity.PlaylistEntity;
import com.company.enums.ProfileRole;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.MethodNotAllowedException;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.mapper.CustomPlaylistRepository;
import com.company.repository.mapper.PlayListFullInfoRepository;
import com.company.repository.mapper.PlayListShortInfoRepository;
import com.company.repository.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PlaylistService {
    @Value("${spring.server.url}")
    private String serverUrl;
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ChannelService channelService;


    public String create(PlaylistDTO dto) {
        PlaylistEntity entity = toEntity(dto);
        playlistRepository.save(entity);
        return "Successfully saved a playlist";
    }

    private PlaylistEntity toEntity(PlaylistDTO dto) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setName(dto.getName());
        entity.setChannelId(dto.getChannelId());
        entity.setPreviewId(dto.getPreviewId());
        entity.setOrder(dto.getOrder());

        return entity;
    }

    public boolean exists(String id) {
        return playlistRepository.existsById(id);
    }

    public List<PlayListShortInfoDTO> listWithShortInfo(Integer id) {
        if (!profileService.exists(id)) {
            log.error("Profile not found!");
            throw new ProfileNotFoundException(
                    "Profile not found!"
            );
        }

        List<PlayListShortInfoRepository> all = playlistRepository.
                findAllByProfileIdOrderByOrderNumberDesc(id);

        List<PlayListShortInfoDTO> dtoList = new LinkedList<>();

        all.forEach(playlistEntity -> {
            PlayListShortInfoDTO dto = PlayListShortInfoDTO(playlistEntity);
            dto.setVideoCount(all.size());

            dtoList.add(dto);
        });

        return dtoList;
    }

    private int getCount(Integer id) {
        return videoService.getCount(id);
    }

    private PlayListShortInfoDTO PlayListShortInfoDTO(PlayListShortInfoRepository entity) {
        PlayListShortInfoDTO dto = new PlayListShortInfoDTO();
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(entity.getPeId());
        playlistDTO.setName(entity.getPeName());
        playlistDTO.setCreatedDate(entity.getPeCreatedDate());

        dto.setPlaylist(playlistDTO);

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(entity.getCId());
        channelDTO.setName(entity.getCName());

        dto.setChannel(channelDTO);

        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(entity.getVId());
        videoDTO.setName(entity.getVName());

        dto.setVideo(videoDTO);

        return dto;
    }

    public List<PlayListFullInfoDTO> listWithFullInfo(Integer id) {
        if (!profileService.exists(id)) {
            log.error("Profile not found!");
            throw new ProfileNotFoundException(
                    "Profile not found!"
            );
        }

        List<PlayListFullInfoRepository> all =
                playlistRepository.findAllByProfileIdWithFullInfo(id);

        List<PlayListFullInfoDTO> dtoList = new LinkedList<>();

        all.forEach(entity -> {
            PlayListFullInfoDTO dto = toDTO(entity);

            dtoList.add(dto);
        });

        return dtoList;
    }

    private PlayListFullInfoDTO toDTO(PlayListFullInfoRepository entity) {
        PlayListFullInfoDTO dto = new PlayListFullInfoDTO();

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(entity.getPeId());
        playlistDTO.setName(entity.getPeName());
        playlistDTO.setStatus(entity.getPeStatus());
        playlistDTO.setOrder(entity.getPeOrder());

        AttachDTO cPhoto = new AttachDTO();
        cPhoto.setId(entity.getCPhotoId());
        cPhoto.setUrl(serverUrl + "/api/v1/attach/" + entity.getCPhotoId());

        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(entity.getCId());
        channelDTO.setName(entity.getCName());
        channelDTO.setPhoto(cPhoto);

        AttachDTO pPhoto = new AttachDTO();
        cPhoto.setId(entity.getPPhotoId());
        cPhoto.setUrl(serverUrl + "/api/v1/attach/" + entity.getPPhotoId());

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getPId());
        profileDTO.setUsername(entity.getPUsername());
        profileDTO.setPhoto(pPhoto);

        dto.setPlaylist(playlistDTO);
        dto.setChannel(channelDTO);
        dto.setProfile(profileDTO);

        return dto;
    }

    public String update(PlaylistDTO dto, String id) {
        Optional<PlaylistEntity> optional =
                playlistRepository.findById(id);

        if (optional.isEmpty()) {
            log.error("Playlist not found!");
            throw new ItemNotFoundException(
                    "Playlist not found!"
            );
        }

        PlaylistEntity entity = optional.get();

        if (!profileService.getCurrentUser().
                getProfile().getId().
                equals(entity.getChannel().getProfileId())) {
            log.error("Method not allowed");
            throw new MethodNotAllowedException(
                    "Method not allowed"
            );
        }

        entity.setOrder(dto.getOrder());
        entity.setName(dto.getName());
        entity.setChannelId(dto.getChannelId());
        entity.setPreviewId(dto.getPreviewId());

        playlistRepository.save(entity);

        return "Successfully updated a playlist";
    }

    public String changeStatus(PlaylistStatusDTO dto, String id) {
        if (!playlistRepository.existsByIdAndVisible(id, Boolean.TRUE)) {
            log.error("Playlist not found!");
            throw new ItemNotFoundException(
                    "Playlist not found!"
            );
        }

        playlistRepository.changeStatus(dto.getStatus(), id);

        return "Successfully changed a playlist's status";


    }

    public String delete(String id) {
        Optional<PlaylistEntity> optional = playlistRepository.
                findByIdAndVisible(id, Boolean.TRUE);

        if (optional.isEmpty()) {
            log.error("Playlist not found!");
            throw new ItemNotFoundException(
                    "Playlist not found!"
            );
        }

        PlaylistEntity entity = optional.get();

        CustomUserDetails user = profileService.getCurrentUser();

        if (!user.getProfile().getId().equals(
                entity.getChannel().getProfileId())
                && !user.getProfile().getRole().equals(ProfileRole.ROLE_ADMIN)) {
            log.error("Method not allowed!");
            throw new MethodNotAllowedException(
                    "Method not allowed!"
            );
        }

        playlistRepository.changeVisible(Boolean.FALSE, id);

        return "Successfully deleted a playlist";
    }

    public List<PlayListFullInfoDTO> pagination(int size, int page) {

        List<PlayListFullInfoRepository> all = playlistRepository.
                findAllByProfileIdWithFullInfoWithPagination(page, size);

        if (all == null) {
            log.error("No playlists yet");
            throw new ItemNotFoundException(
                    "No playlists yet"
            );
        }

        List<PlayListFullInfoDTO> dtoList = new LinkedList<>();

        all.forEach(playListFullInfoRepository -> {
            PlayListFullInfoDTO dto = toDTO(playListFullInfoRepository);

            dtoList.add(dto);
        });

        return dtoList;

    }

    public List<PlayListFullInfoDTO> listByChannel(String id) {
        if (!channelService.exists(id)) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }

        List<PlayListFullInfoRepository> all = playlistRepository.findAllByChannelIdWithShortInfo(id);

        if (all == null) {
            log.error("No playlists created by this channel yet!");
            throw new ItemNotFoundException(
                    "No playlists created by this channel yet!"
            );
        }

        List<PlayListFullInfoDTO> dtoList = new LinkedList<>();

        all.forEach(playListFullInfoRepository -> {
            PlayListFullInfoDTO dto = toDTO(playListFullInfoRepository);

            dtoList.add(dto);
        });

        return dtoList;
    }

    public CustomPlaylistDTO getById(String id) {
        Optional<CustomPlaylistRepository> optional
                = playlistRepository.getById(id);

        if (optional.isEmpty()) {
            log.error("Playlist not found!");
            throw new ItemNotFoundException(
                    "Playlist not found!"
            );
        }

        CustomPlaylistRepository customPlaylistRepository =
                optional.get();

        return toDTO(customPlaylistRepository);
    }

    private CustomPlaylistDTO toDTO(CustomPlaylistRepository entity) {
        CustomPlaylistDTO dto = new CustomPlaylistDTO();
        dto.setPlaylistId(entity.getPlaylistId());
        dto.setPlaylistName(entity.getPlaylistName());
        dto.setPlaylistUpdatedDate(entity.getPlaylistUpdatedDate());

        dto.setTotalViewCount(entity.getTotalViewCount());
        dto.setVideoCount(entity.getVideoCount());

        return dto;
    }
}
