package com.example.ordermanagementsystem.controller;

import com.example.ordermanagementsystem.dto.RequestOrdersDto;
import com.example.ordermanagementsystem.entity.Products;
import com.example.ordermanagementsystem.service.OrdersService;
import com.example.ordermanagementsystem.service.ProductsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/"})
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private OrdersService ordersService;

    @PostMapping(value={"/saveProduct"})
    public List<Products> saveProduct(@RequestBody List<Products> products) {
        return this.productsService.saveProduct(products);
    }

    @GetMapping(value={"/getAllProducts"})
    public List<Products> getAllProducts() {
        return this.productsService.getAllProducts();
    }

    @PostMapping(value={"/purchase"})
    public String purchaseProduct(@RequestBody RequestOrdersDto requestOrdersDto) {
        String error = this.ordersService.purchaseProduct(requestOrdersDto);
        if (!error.isEmpty()) {
            return error;
        }
        return "Success";
    }
}
