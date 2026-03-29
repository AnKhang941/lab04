package com.example.lab04.model;

import lombok.Data;

@Data
public class CartItem {
    private Long id;
    private String name;
    private String image;
    private double price;
    private int quantity;
}