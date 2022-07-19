package com.company.repository;

import com.company.entity.CategoryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer> {
    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("update CategoryEntity set name=?1 where id=?2")
    void update(String name, Integer id);

    boolean existsByIdAndVisible(Integer id, Boolean visible);

    @Modifying
    @Transactional
    @Query("update CategoryEntity set visible=?1 where id=?2")
    void changeVisible(Boolean visible,Integer id);

    List<CategoryEntity> findAllByVisible(Boolean visible);
}
