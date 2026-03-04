package com.example.lab04.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name="sanpham")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Tên sản phẩm không được để trống")
    @Column(nullable = false,length=255)
    private String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1,message = "Giả sản phẩm không được nhỏ hơn 1")
    @Max(value = 9999999,message ="Giá sản phầm không được lớn hơn 9999999")
    @Column(nullable = false)
    private long price;

    @Length(min=0,max=200,message = "Tên hình ành không quá 200 kí tự")
    @Column(length=200)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

}
