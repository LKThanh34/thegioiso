package com.lekimthanh.thegioiso.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lekimthanh.thegioiso.domain.Category;
import com.lekimthanh.thegioiso.service.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/create";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute Category category,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "admin/categories/create";
        }
        try {
            categoryService.createCategory(category);
            return "redirect:/categories";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/categories/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Category category = categoryService.getCategoryById(id);
            model.addAttribute("category", category);
            return "admin/categories/edit";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/categories";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,
            @Valid @ModelAttribute Category category,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "admin/categories/edit";
        }
        try {
            categoryService.updateCategory(id, category);
            return "redirect:/categories";
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/categories/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            categoryService.deleteCategory(id);
            return "redirect:/categories";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/categories";
        }
    }

}
