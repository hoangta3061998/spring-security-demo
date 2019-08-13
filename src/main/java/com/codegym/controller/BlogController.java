package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping({"/blog"})
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/home")
    public ModelAndView home(Pageable pageable) {
        Page<Blog> blogs = blogService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/blog/home");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping({"/edit/{id}"})
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        Blog blog = blogService.findById(id);
        modelAndView.addObject("blog", blog);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute("blog") Blog blog) {
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        blogService.save(blog);
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("message","Edited");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute("blog") Blog blog) {
        blog.setCreatedAt(new Date());
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("message", "Created!");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @GetMapping({"/delete/{id}"})
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/blog/delete");
        modelAndView.addObject("blog", blogService.findById(id));
        return modelAndView;
    }
    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute("blog") Blog blog){
        ModelAndView modelAndView = new ModelAndView("/blog/home");
        modelAndView.addObject("message","Deleted!");
        blogService.remove(blog.getId());
        return modelAndView;
    }
    @GetMapping({"/view/{id}"})
    public ModelAndView view(@PathVariable("id")Long id){
        ModelAndView modelAndView = new ModelAndView("/blog/view");
        modelAndView.addObject("blog",blogService.findById(id));
        return modelAndView;
    }
    @PostMapping({"/search"})
    public ModelAndView search(@RequestParam("search") String search,Pageable pageable){
        List<Blog> blogs = blogService.findAll(pageable).getContent();
        Long id = -1L;
        for ( int i = 0 ; i < blogs.size();i++){
            if(blogs.get(i).getTitle().equals(search)){
                id = blogs.get(i).getId();
                break;
            }
        }
        if(id != -1L){
            ModelAndView modelAndView = new ModelAndView("/blog/view");
            modelAndView.addObject("blog",blogService.findById(id));
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/blog/home");
        modelAndView.addObject("message","No result");
        return modelAndView;
    }

}
