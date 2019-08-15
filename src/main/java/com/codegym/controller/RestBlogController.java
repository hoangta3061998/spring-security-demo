package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restblog")
public class RestBlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/home")
    public ResponseEntity<Page<Blog>> findAll(Pageable pageable) {
        Page<Blog> blogs = blogService.findAll(pageable);
        return new ResponseEntity<Page<Blog>>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> findById(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);
        return new ResponseEntity<Blog>(blog,HttpStatus.OK);
    }
}
