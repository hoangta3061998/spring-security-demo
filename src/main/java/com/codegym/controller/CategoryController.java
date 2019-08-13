package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.BlogService;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping({"/category"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/home")
    public ModelAndView home() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/home");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping({"/edit/{id}"})
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        Category category = categoryService.findById(id);
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute("category") Category category) {
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        categoryService.save(category);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message","Edited");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("message", "Created!");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @GetMapping({"/delete/{id}"})
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category", categoryService.findById(id));
        return modelAndView;
    }
    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute("category") Category category){
        ModelAndView modelAndView = new ModelAndView("/category/home");
        modelAndView.addObject("message","Deleted!");
        categoryService.remove(category.getId());
        return modelAndView;
    }
    @GetMapping({"/view/{id}"})
    public ModelAndView view(@PathVariable("id")Long id){
        Category category = categoryService.findById(id);
        if(category==null){
            return new ModelAndView("error-404");
        }
        Iterable<Blog> blogs = blogService.findAllByCategory(category);
        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category",category);
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }
}
