package com.company.repository;

import com.company.entity.comment.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity,Integer> {
    Iterable<CommentEntity> findAllByVisible(boolean b);
}
