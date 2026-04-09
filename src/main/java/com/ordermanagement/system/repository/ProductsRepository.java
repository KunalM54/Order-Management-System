package com.ordermanagement.system.repository;

import com.ordermanagement.system.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository
extends JpaRepository<Products, Long> {
}
