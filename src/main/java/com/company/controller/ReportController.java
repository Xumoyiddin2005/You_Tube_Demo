package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.dto.ReportDTO;
import com.company.dto.category.CategoryDTO;
import com.company.enums.ProfileRole;
import com.company.service.AttachService;
import com.company.service.ReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@Controller
@RequestMapping("api/v1/report")
public class ReportController {
    @Autowired
    @Lazy
    private ReportService reportService;
    @PostMapping("/adm/create")
    private ResponseEntity<ReportDTO> create(@RequestBody ReportDTO dto) {
        return ResponseEntity.ok(reportService.reportCreate(dto));
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl> pagination(@RequestParam(value = "page" , defaultValue = "1") int page,
                                               @RequestParam(value = "size" ,defaultValue = "5" ) int size){
        PageImpl response = reportService.paginationProfile(page , size);
        return ResponseEntity.ok().body(response);
    }
}
