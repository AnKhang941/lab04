package com.example.lab04.service;

import com.example.lab04.model.Category;
import com.example.lab04.model.Product;
import com.example.lab04.repository.CategoryRepository;
import com.example.lab04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
    public List<Product> GetSearchProducts(String key) {
        return productRepository.findByNameContainingIgnoreCase(key);
    }
    public Page<Product> getProductByPage(int page, int pagesize) {
        return  productRepository.findAll(PageRequest.of(page, pagesize));
    }
    public Page<Product> getProductByPage(int page, int pagesize, String sort, Integer categoryId) {
        Sort sortOrder = Sort.unsorted();

        if ("asc".equals(sort)) {
            sortOrder = Sort.by("price").ascending();
        } else if ("desc".equals(sort)) {
            sortOrder = Sort.by("price").descending();
        }

        Pageable pageable = PageRequest.of(page, pagesize, sortOrder);

        if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId, pageable);
        }

        return productRepository.findAll(pageable);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
