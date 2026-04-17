package com.example.ordermanagementsystem.service;

import com.example.ordermanagementsystem.dto.BillDetails;
import com.example.ordermanagementsystem.dto.RequestOrdersDto;
import com.example.ordermanagementsystem.entity.Orders;
import com.example.ordermanagementsystem.entity.Products;
import com.example.ordermanagementsystem.repository.OrdersRepository;
import com.example.ordermanagementsystem.repository.ProductsRepository;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private SmsAndWhatsAppService smsAndWhatsAppService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public String purchaseProduct(RequestOrdersDto requestOrdersDto) {

        String validationError = this.validateRequest(requestOrdersDto);

        if (!validationError.isEmpty()) {
            return validationError;
        }

        Optional<Products> optionalProduct = this.productsRepository.findById(requestOrdersDto.getProductId());
        if (optionalProduct.isEmpty()) {
            return "Product Not Found";
        }

        Products product = optionalProduct.get();
        if (product.getStock() < requestOrdersDto.getQuantity()) {
            return "Product is out of stock";
        }

        BillDetails bill = this.generateBill(requestOrdersDto, product);
        boolean payment = this.payment();

        if (!payment) {
            this.smsAndWhatsAppService.sendPaymentFailedMessage(requestOrdersDto.getMobileNumber(), requestOrdersDto);
            return "Payment Failed Due to Some Reason,Please Try Again";
        }

        product.setStock(product.getStock() - requestOrdersDto.getQuantity());
        this.productsRepository.save(product);

        if (product.getStock() <= product.getThreshHold()) {
            this.emailService.sendLowStockAlert(product);
        }

        Orders orders = new Orders();
        orders.setName(requestOrdersDto.getName());
        orders.setMobileNumber(requestOrdersDto.getMobileNumber());
        orders.setProductId(requestOrdersDto.getProductId());
        orders.setQuantity(requestOrdersDto.getQuantity());
        orders.setTotal(bill.getTotal());
        orders.setGstOnTotal(bill.getGst());
        orders.setTotalAmount(bill.getFinalAmount());
        Orders savedOrder = (Orders)this.ordersRepository.save(orders);

        this.smsAndWhatsAppService.sendWhatsAppMessage(requestOrdersDto.getMobileNumber(), savedOrder);
        return "Successfully Order Placed, Order details sent to your mobile number";
    }

    public String validateRequest(RequestOrdersDto requestOrdersDto) {
        StringBuilder response = new StringBuilder();
        if (requestOrdersDto.getName() == null || requestOrdersDto.getName().trim().isEmpty()) {
            response.append("Name is Required");
        }
        if (requestOrdersDto.getMobileNumber() == null || requestOrdersDto.getMobileNumber().trim().isEmpty()) {
            response.append("Mobile Number is Required \n");
        } else if (requestOrdersDto.getMobileNumber().length() != 10) {
            response.append("Mobile Number must be 10 digits \n");
        }
        if (requestOrdersDto.getProductId() == 0L) {
            response.append("Product id is Required \n");
        }
        if (requestOrdersDto.getQuantity() <= 0) {
            response.append("Quantity should be at least 1 \n");
        }
        return response.toString();
    }

    public BillDetails generateBill(RequestOrdersDto dto, Products product) {
        int quantity = dto.getQuantity();
        double unitPrice = product.getPrice();
        double totalPrice = unitPrice * (double)quantity;
        double gst = totalPrice * 18.0 / 100.0;
        double finalAmount = totalPrice + gst;
        return new BillDetails(totalPrice, gst, finalAmount);
    }

    public boolean payment() {
        boolean[] arr = new boolean[]{true, false, true, false};
        Random random = new Random();
        int index = random.nextInt(arr.length);
        return arr[index];
    }
}
