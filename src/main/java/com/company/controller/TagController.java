package com.company.controller;

import com.company.dto.TagDTO;
import com.company.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Controller
@RequestMapping("api/v1/tag")
public class TagController {
    @Autowired
    private  TagService tagService;



    @ApiOperation(value = "Create", notes="Method for creating a tag")
    @PostMapping("/public/create")
    public ResponseEntity<String> create(@RequestBody @Valid TagDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @ApiOperation(value = "Update", notes="Method for updating a tag")
    @PutMapping("/adm/update/{id}")
    private ResponseEntity<String> update(@RequestBody @Valid TagDTO dto,
                                         @PathVariable("id") Integer id) {
        return ResponseEntity.ok(tagService.update(dto,id));
    }

    @ApiOperation(value = "Delete", notes="Method for deleting a tag")
    @DeleteMapping("/adm/delete/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(tagService.delete(id));
    }

    @ApiOperation(value = "List", notes="Method for getting a tag list")
    @GetMapping("/public/list")
    private ResponseEntity<List<TagDTO>> list() {
        return ResponseEntity.ok(tagService.list());
    }


}
