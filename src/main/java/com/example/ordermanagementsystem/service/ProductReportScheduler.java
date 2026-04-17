package com.example.ordermanagementsystem.service;

import com.example.ordermanagementsystem.entity.Products;
import com.example.ordermanagementsystem.repository.ProductsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ProductReportScheduler {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CsvService csvService;

    @Autowired
    private EmailService emailService;

    @Value("${app.report.email.to:${spring.mail.username:}}")
    private String reportToEmail;

    @Scheduled(cron = "0 * * * * ?")
    public void sendDailyProductReport() {
        List<Products> products = productsRepository.findAll();
        String filePath = csvService.generateCsv(products);

        emailService.sendEmailWithAttachment(
                reportToEmail,
                "Daily Product Report",
                "Attached is the product report.",
                filePath
        );
    }
}
