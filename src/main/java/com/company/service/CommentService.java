package com.company.service;


import com.company.dto.comment.CommentDTO;
import com.company.entity.comment.CommentEntity;
import com.company.entity.video.VideoEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private VideoService videoService;

//    public CommentDTO create(CommentDTO dto) {
//        ProfileEntity profile = profileService.getProfile();
//
//        CommentEntity entity = new CommentEntity();
//        entity.setContent(dto.getContent());
//        entity.setProfileId(profile.getId());
//        entity.setVideoId(dto.getVideoId());
//
//        return null;
//    }



    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());

        VideoEntity video = videoService.get(dto.getVideoId());
        entity.setVideo(video);
        entity.setProfile(profileService.getProfile());

        commentRepository.save(entity);

        return dto;
    }


    public List<CommentDTO> getList() {

        Iterable<CommentEntity> all = commentRepository.findAllByVisible(true);
        List<CommentDTO> dtoList = new LinkedList<>();

        all.forEach(article -> dtoList.add(toDTO(article)));
        return dtoList;
    }

    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public void delete(Integer id) {
        Optional<CommentEntity> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new ItemNotFoundException("Mazgi bu idli comment yo'q");
        }
        CommentEntity entity = comment.get();
        entity.setVisible(false);
        commentRepository.save(entity);
    }

    public String update(Integer id, CommentDTO dto) {

        Optional<CommentEntity> optional = commentRepository.findById(id);

        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Bunday id li comment yo'q");
        }


        CommentEntity entity = optional.get();
        entity.setContent(dto.getContent());

        commentRepository.save(entity);

        return "Succesfully updated";

    }




}
