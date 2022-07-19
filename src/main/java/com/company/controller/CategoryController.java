package com.company.controller;

import com.company.dto.category.CategoryDTO;
import com.company.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private  CategoryService categoryService;


    @PostMapping("/adm/create")
    private ResponseEntity<String> create(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/adm/update/{id}")
    private ResponseEntity<String> update(@RequestBody CategoryDTO dto,
                                          @PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(categoryService.update(dto,categoryId));
    }

    @DeleteMapping("/adm/delete/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(categoryService.delete(categoryId));
    }

    @GetMapping("/public/list")
    public ResponseEntity<List<CategoryDTO>> list() {
        return ResponseEntity.ok(categoryService.publicList());
    }
}
