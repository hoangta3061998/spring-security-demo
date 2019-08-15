package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restcategory")
public class RestCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Category>> findAll() {
        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> findById(@PathVariable("id") Long id, Pageable pageable) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/blogs")
    public ResponseEntity<Page<Blog>> findBlogs(@PathVariable("id") Long id, Pageable pageable) {
        Category category = categoryService.findById(id);
        Page<Blog> blogs = blogService.findByCategory(category, pageable);
        return new ResponseEntity<Page<Blog>>(blogs,HttpStatus.OK);
    }
}
