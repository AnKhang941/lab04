package com.example.lab04.repository;
import com.example.lab04.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {}