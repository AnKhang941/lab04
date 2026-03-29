package com.example.lab04.controller;

import com.example.lab04.model.Order;
import com.example.lab04.service.CartService;
import com.example.lab04.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.lab04.model.CartItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/list";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id,
                            @RequestHeader(value = "Referer", required = false) String referer) {
        cartService.addToCart(id);
        return referer != null ? "redirect:" + referer : "redirect:/products";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam int productId,
                                 @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable int id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }

    @PostMapping("/order")
    public String order(Model model) {
        List<CartItem> cartItems = cartService.getItems();

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }


        Order order = orderService.checkout(cartItems);

        model.addAttribute("order", order);
        model.addAttribute("cartItems", cartItems);


        cartService.clear();

        return "cart/order-success";
    }
}