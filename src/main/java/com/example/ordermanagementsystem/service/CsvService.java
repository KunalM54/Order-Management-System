package com.example.ordermanagementsystem.service;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.example.ordermanagementsystem.entity.Products;
import org.springframework.stereotype.Service;

@Service
public class CsvService {
    public String generateCsv(List<Products> products) {
        String filePath = "products_report.csv";
        try (FileWriter fileWriter = new FileWriter(filePath);){
            fileWriter.append("Id,Name,Price,Stock,ThresHold\n");
            for (Products product : products) {
                fileWriter.append(String.valueOf(product.getId())).append(",");
                fileWriter.append(product.getProductName()).append(",");
                fileWriter.append(String.valueOf(product.getPrice())).append(",");
                fileWriter.append(String.valueOf(product.getStock())).append(",");
                fileWriter.append(String.valueOf(product.getThreshHold())).append("\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }
}
