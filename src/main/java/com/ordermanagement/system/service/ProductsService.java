package com.ordermanagement.system.service;

import com.ordermanagement.system.entity.Products;
import com.ordermanagement.system.repository.ProductsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> saveProduct(List<Products> products) {
        return this.productsRepository.saveAll(products);
    }

    public List<Products> getAllProducts() {
        return this.productsRepository.findAll();
    }
}
