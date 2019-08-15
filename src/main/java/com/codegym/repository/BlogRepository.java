package com.codegym.repository;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {

    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);

    //Page<Blog> findAll(Pageable pageable);
    Iterable<Blog> findAllByCategory(Category category);
    @Query("select new Blog(b.id, b.title, b.teaser, b.feature, b.created, b.likes, b.category) from Blog b where b.category = :category")
    Page<Blog> findByCategory(@Param("category") Category category, Pageable pageable);

    @Query("select new Blog(b.id, b.title, b.teaser, b.feature, b.created, b.likes, b.category) from Blog b")
    Page<Blog> findAllSummary(Pageable pageable);
}
