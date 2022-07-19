package com.company.repository;

import com.company.entity.ReportEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReportRepository extends PagingAndSortingRepository<ReportEntity, Integer> {
}
