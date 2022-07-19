package com.company.repository;

import com.company.entity.video.PlaylistVideoEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistVideoRepository extends CrudRepository<PlaylistVideoEntity,String> {
}
