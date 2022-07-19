package com.company.service;

import com.company.dto.ReportDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.ReportEntity;
import com.company.enums.EntityType;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ProfileService profileService;

    public ReportDTO reportCreate(ReportDTO dto) {
        ReportEntity report = new ReportEntity();
        report.setContent(dto.getContent());
        report.setEntityId(dto.getEntityId());
        report.setType(dto.getType());
        report.setProfile(profileService.getProfile());

        reportRepository.save(report);
        return dto;
    }

    public PageImpl paginationProfile(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ReportEntity> all = reportRepository.findAll(pageable);

        List<ReportDTO> dtoList = new LinkedList<>();

        all.getContent().forEach(report -> {
            ReportDTO dto = new ReportDTO();
            dto.setContent(report.getContent());
            dto.setEntityId(report.getEntityId());
            dto.setType(report.getType());
            dtoList.add(dto);
        });

        return new PageImpl(dtoList, pageable, all.getTotalElements());
    }

}
