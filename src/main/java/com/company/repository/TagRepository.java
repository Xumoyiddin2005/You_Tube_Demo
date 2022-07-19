package com.company.repository;

import com.company.entity.TagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("update TagEntity set name=:name where id=:id")
    void update(@Param("name") String name,
                @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("update TagEntity set visible=:visible where id=:id")
    void updateVisible(@Param("visible") Boolean visible,
                       @Param("id") Integer id);

    boolean existsByIdAndVisible(Integer id, Boolean visible);

    List<TagEntity> findAllByVisible(Boolean aTrue);
}
