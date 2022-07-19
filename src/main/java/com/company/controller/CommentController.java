package com.company.controller;
// PROJECT NAME -> You_Tube_Demo
// TIME -> 15:59
// MONTH -> 07
// DAY -> 13

import com.company.dto.comment.CommentDTO;
import com.company.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    //PUBLIC


    //ADMIN

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CommentDTO articleDTO) {
        commentService.create(articleDTO);
        return ResponseEntity.ok().body("Successfully created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody CommentDTO commentDTO) {
        commentService.update(id, commentDTO);
        return ResponseEntity.ok().body("Successfully updated");
    }


    @GetMapping("/getList")
    public ResponseEntity<?> getArticleList() {
        List<CommentDTO> list = commentService.getList();
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        commentService.delete(id);
        ResponseEntity<Object> build = ResponseEntity.ok().body("Successfully deleted");
        return build;
    }

}
