package com.example.lab04.service;

import com.example.lab04.model.CartItem;
import com.example.lab04.model.Product;
import com.example.lab04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {

    @Autowired
    ProductRepository productRepository;

    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addToCart(int productId) {
        Product findProduct = productRepository.findById(productId).orElse(null);
        if (findProduct == null) { return; }

        items.stream()
                .filter(item -> item.getId().equals(findProduct.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setId(findProduct.getId());
                            newItem.setName(findProduct.getName());
                            newItem.setImage(findProduct.getImage());
                            newItem.setPrice(findProduct.getPrice());
                            newItem.setQuantity(1);
                            items.add(newItem);
                        }
                );
    }

    public void updateQuantity(int productId, int quantity) {
        items.stream()
                .filter(item -> item.getId().equals((long) productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void removeFromCart(int productId) {
        items.removeIf(item -> item.getId().equals((long) productId));
    }

    public void clear() {
        items.clear();
    }

    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}