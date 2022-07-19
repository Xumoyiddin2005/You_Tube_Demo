package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.service.AttachService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Controller
@RequestMapping("api/v1/attach")
public class AttachController {
    @Autowired
    @Lazy
    private AttachService attachService;


    @ApiOperation(value = "Create", notes="Method for creating an attach")
    @PostMapping("/create")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.create(file));
    }

    @ApiOperation(value = "Get", notes="Method for getting an attach by id")
    @GetMapping(value = "/{id}",produces = MediaType.ALL_VALUE)
    public byte[] getById(@PathVariable("id") String id) {
        return attachService.getById(id);
    }

    @ApiOperation(value = "Download", notes="Method for downloading an attach by id")
    @GetMapping(value = "/download/{id}",produces = MediaType.ALL_VALUE)
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        return attachService.download(id);
    }

    @ApiOperation(value = "Delete", notes="Method for deleting an attach by id")
    @DeleteMapping(value = "/adm/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }

    @ApiOperation(value = "Pagination", notes="Method for pagination")
    @GetMapping(value = "/adm/pagination")
    public ResponseEntity<List<AttachDTO>> pagination(@RequestParam("size") int size,
                                                      @RequestParam("page") int page) {
        return ResponseEntity.ok(attachService.pagination(size,page));
    }






}
