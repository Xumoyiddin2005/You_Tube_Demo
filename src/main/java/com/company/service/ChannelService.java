package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.channel.ChannelAttachDTO;
import com.company.dto.channel.ChannelStatusDTO;
import com.company.dto.channel.ChannelCreateDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.channel.ChannelUpdateDTO;
import com.company.entity.ChannelEntity;
import com.company.enums.ProfileRole;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.MethodNotAllowedException;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChannelService {
    @Autowired
    private  ChannelRepository channelRepository;
    @Autowired
    @Lazy
    private AttachService attachService;

    @Autowired
    @Lazy
    private ProfileService profileService;


    public boolean existsPhoto(String id) {
        return channelRepository.existsByPhotoIdOrBannerId(id,id);
    }

    public String create(ChannelCreateDTO dto) {
        if (!attachService.exists(dto.getPhotoId())) {
            log.error("Photo not found!");
            throw new ItemNotFoundException(
                    "Photo not found!"
            );
        }

        if (!profileService.exists(dto.getProfileId())) {
            log.error("Profile not found!");
            throw new ProfileNotFoundException(
                    "Profile not found!"
            );
        }

        ChannelEntity entity = toEntity(dto);

        if (dto.getBannerId() != null && attachService.exists(dto.getBannerId())) {
            entity.setBannerId(dto.getBannerId());
        }

        channelRepository.save(entity);

        return "Successfully saved a channel";

    }

    public String update(ChannelUpdateDTO dto,String id) {
        Optional<ChannelEntity> optional = channelRepository.findByIdAndVisible(id,Boolean.TRUE);

        if (optional.isEmpty()) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }

        ChannelEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setTelegramUrl(dto.getTelegramUrl());
        entity.setWebsiteUrl(dto.getWebsiteUrl());

        if (!profileService.getCurrentUser().getProfile()
                .getId().equals(entity.getProfileId())) {
            log.error("Method not allowed");
            throw new MethodNotAllowedException(
                    "Method not allowed"
            );
        }

        channelRepository.save(entity);

        return "Successfully update a channel";

    }

    private ChannelEntity toEntity(ChannelCreateDTO dto) {
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setTelegramUrl(dto.getTelegramUrl());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setProfileId(dto.getProfileId());
        entity.setPhotoId(dto.getPhotoId());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setTelegramUrl(dto.getTelegramUrl());
        entity.setInstagramUrl(dto.getInstagramUrl());

        return entity;
    }

    public boolean exists(String channelId) {
        return channelRepository.existsById(channelId);
    }

    public String updatePhoto(String id, ChannelAttachDTO dto) {
        CustomUserDetails user = profileService.getCurrentUser();

        if (!attachService.exists(dto.getAttachId())) {
            log.error("Photo not found!");
            throw new ItemNotFoundException(
                    "Photo not found!"
            );
        }

        Optional<ChannelEntity> optional =
                channelRepository.findByIdAndVisible(id, Boolean.TRUE);
        if (optional.isEmpty()) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }

        ChannelEntity entity = optional.get();


        if (!entity.getProfileId().equals(user.getProfile().getId())) {
            log.error("Method not allowed");
            throw new MethodNotAllowedException(
                    "Method not allowed"
            );
        }

        entity.setPhotoId(dto.getAttachId());

        channelRepository.updatePhoto(entity.getPhotoId(),entity.getId());

        return "Successfully updated a channel's photo";
    }

    public String updateBanner(String id, ChannelAttachDTO dto) {
        CustomUserDetails user = profileService.getCurrentUser();

        if (!attachService.exists(dto.getAttachId())) {
            log.error("Photo not found!");
            throw new ItemNotFoundException(
                    "Photo not found!"
            );
        }

        Optional<ChannelEntity> optional =
                channelRepository.findByIdAndVisible(id, Boolean.TRUE);
        if (optional.isEmpty()) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }

        ChannelEntity entity = optional.get();

        if (!entity.getProfileId().equals(user.getProfile().getId())) {
            log.error("Method not allowed");
            throw new MethodNotAllowedException(
                    "Method not allowed"
            );
        }

        entity.setPhotoId(dto.getAttachId());

        channelRepository.updateBanner(entity.getPhotoId(),entity.getId());

        return "Successfully updated a channel's banner";
    }

    public List<ChannelDTO> pagination(int size, int page) {
        Sort sort = Sort.by(Sort.Direction.ASC,"createdDate");
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<ChannelEntity> all = channelRepository.findAll(pageable);

        if (all == null) {
            log.error("No channels yet");
            throw new ItemNotFoundException(
                    "No channels yet"
            );
        }

        List<ChannelDTO> dtoList = new LinkedList<>();

        all.forEach(channelEntity -> {
            ChannelDTO dto = toDTO(channelEntity);

            dtoList.add(dto);
        });
        return dtoList;
    }

    private ChannelDTO toDTO(ChannelEntity entity) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhotoId(entity.getPhotoId());
        dto.setBannerId(entity.getBannerId());
        dto.setInstagramUrl(entity.getInstagramUrl());
        dto.setProfileId(entity.getProfileId());
        dto.setTelegramUrl(entity.getTelegramUrl());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setWebsiteUrl(entity.getWebsiteUrl());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    public ChannelDTO getById(String id) {
        Optional<ChannelEntity> optional = channelRepository.findByIdAndVisible(id, Boolean.TRUE);

        if (optional.isEmpty()) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }

        ChannelEntity entity = optional.get();

        return toDTO(entity);
    }

    public String changeStatus(String id, ChannelStatusDTO dto) {
        Optional<ChannelEntity> optional = channelRepository.findByIdAndVisible(id, Boolean.TRUE);

        if (optional.isEmpty()) {
            log.error("Channel not found!");
            throw new ItemNotFoundException(
                    "Channel not found!"
            );
        }
        ChannelEntity entity = optional.get();

        if (!profileService.getCurrentUser()
                .getProfile().getId().equals(
                        entity.getProfileId()) &&
                !profileService.getCurrentUser().getProfile()
                        .getRole().equals(ProfileRole.ROLE_ADMIN)) {
            log.error("Method not allowed!");
            throw new MethodNotAllowedException(
                    "Method not allowed!"
            );
        }

        entity.setStatus(dto.getStatus());


        channelRepository.updateStatus(entity.getStatus(),id);

        return "Successfully updated a channel's status";
    }

    public List<ChannelDTO> getListByProfileId(Integer id) {
        if (!profileService.exists(id)) {
            log.error("Profile not found!");
            throw new ProfileNotFoundException(
                    "Profile not found!"
            );
        }

        List<ChannelEntity> entities = channelRepository.getAllByProfileId(id);

        if (entities.size() == 0) {
            log.error("No channels created by this profile yet");
            throw new ItemNotFoundException(
                    "No channels created by this profile yet"
            );
        }

        List<ChannelDTO> dtoList = new LinkedList<>();

        entities.forEach(channel -> {
            ChannelDTO dto = toDTO(channel);

            dtoList.add(dto);
        });

        return dtoList;

    }
}
