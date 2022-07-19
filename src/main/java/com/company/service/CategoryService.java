package com.company.service;

import com.company.dto.category.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemAlreadyExistsException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private  CategoryRepository categoryRepository;



    public String create(CategoryDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            log.error("Category not found!");
            throw new ItemNotFoundException(
                    "Category not found!"
            );
        }

        CategoryEntity entity = toEntity(dto);

        categoryRepository.save(entity);

        return "Successfully created a category";
    }

    public String update(CategoryDTO dto,Integer cId) {
        if (!categoryRepository.existsById(cId)) {
            log.error("Category not found!");
            throw new ItemNotFoundException(
                    "Category not found!"
            );
        }

        if (categoryRepository.existsByName(dto.getName())) {
            log.error("Category name is already taken");
            throw new ItemAlreadyExistsException(
                    "Category name is already taken"
            );
        }

        categoryRepository.update(dto.getName(),cId);

        return "Successfully updated a category";
    }

    public String delete(Integer categoryId) {
        if (!categoryRepository.existsByIdAndVisible(categoryId,Boolean.TRUE)) {
            log.error("Category not found!");
            throw new ItemNotFoundException(
                    "Category not found!"
            );
        }

        categoryRepository.changeVisible(Boolean.FALSE,categoryId);

        return "Successfully deleted a category";
    }

    public List<CategoryDTO> publicList() {
        List<CategoryEntity> all = categoryRepository.findAllByVisible(Boolean.TRUE);

        if (all.size() == 0) {
            log.warn("No categories yet");
            throw new BadRequestException(
                    "No categories yet"
            );
        }

        List<CategoryDTO> dtoList = new LinkedList<>();

        all.forEach(category -> {
            CategoryDTO dto = toDTO(category);

            dtoList.add(dto);
        });

        return dtoList;
    }


    private CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());

        return entity;
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public boolean exists(Integer categoryId) {
        return categoryRepository.existsByIdAndVisible(categoryId,Boolean.TRUE);
    }
}
