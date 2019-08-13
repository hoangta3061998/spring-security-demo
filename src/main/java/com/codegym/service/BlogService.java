package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog findById(Long id);

    void save(Blog blog);

    void remove(Long id);


    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
    Page<Blog> findAll(Pageable pageable);
    Iterable<Blog> findAllByCategory(Category category);
}
