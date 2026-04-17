package com.example.ordermanagementsystem.repository;

import com.example.ordermanagementsystem.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository
extends JpaRepository<Products, Long> {
}
