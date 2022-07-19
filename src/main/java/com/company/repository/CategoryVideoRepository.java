package com.company.repository;

import com.company.entity.video.CategoryVideoEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryVideoRepository extends CrudRepository<CategoryVideoEntity,Integer> {
}
