package com.lekimthanh.thegioiso.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lekimthanh.thegioiso.domain.Product;
import com.lekimthanh.thegioiso.repository.CategoryRepository;
import com.lekimthanh.thegioiso.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/products/form";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/products/form";
        }
        try {
            productService.createProduct(product, categoryId);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/products/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("product", productService.getProductById(id));
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/products/form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/products";
        }
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/products/form";
        }
        try {
            productService.updateProduct(id, product, categoryId);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/products/form";
        }
    }

    // Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        try {
            productService.deleteProduct(id);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/products";
        }
    }
}
