package com.example.lab04.controller;

import com.example.lab04.model.Category;
import com.example.lab04.model.Product;
import com.example.lab04.service.CategoryService;
import com.example.lab04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String sort,
                               @RequestParam(required = false) Integer categoryId) {

        Page<Product> products = productService.getProductByPage(page, 5, sort, categoryId);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", productService.getAllCategories());

        return "product/list";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("category.id") Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String index(Model model,
                        @RequestParam(defaultValue = "") String key,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "") String sort,
                        @RequestParam(required = false) Integer categoryId) {

        Page<Product> products = productService.getProductByPage(page, 5);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", productService.getAllCategories());

        return "product/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}