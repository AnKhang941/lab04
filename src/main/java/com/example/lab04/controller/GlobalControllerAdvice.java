
package com.example.lab04.controller;

import com.example.lab04.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartItemCount")
    public int cartItemCount() {
        return cartService.getItems().size();
    }
}