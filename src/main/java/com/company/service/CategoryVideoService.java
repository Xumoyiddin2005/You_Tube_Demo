package com.company.service;

import com.company.dto.category.CategoryVideoDTO;
import com.company.entity.video.CategoryVideoEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CategoryVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryVideoService {
    @Autowired
    private CategoryVideoRepository categoryVideoRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired

    private VideoService videoService;



    public void create(CategoryVideoDTO dto) {
        if (!videoService.exists(dto.getVideoId())) {
            log.error("Video not found!");
            throw new ItemNotFoundException(
                    "Video not found!"
            );
        }

        if (!categoryService.exists(dto.getCategoryId())) {
            log.error("Category not found!");
            throw new ItemNotFoundException(
                    "Category not found!"
            );
        }

        CategoryVideoEntity entity = toEntity(dto);

        categoryVideoRepository.save(entity);

    }

    private CategoryVideoEntity toEntity(CategoryVideoDTO dto) {
        CategoryVideoEntity entity = new CategoryVideoEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setCategoryId(dto.getCategoryId());

        return entity;
    }
}
