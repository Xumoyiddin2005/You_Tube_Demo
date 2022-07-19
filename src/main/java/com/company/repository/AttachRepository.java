package com.company.repository;

import com.company.entity.AttachEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends PagingAndSortingRepository<AttachEntity,String> {
}
