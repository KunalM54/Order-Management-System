package com.example.ordermanagementsystem.service;

import com.example.ordermanagementsystem.dto.RequestOrdersDto;
import com.example.ordermanagementsystem.entity.Orders;
import com.example.ordermanagementsystem.entity.Products;
import com.example.ordermanagementsystem.repository.ProductsRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsAndWhatsAppService {

    @Autowired
    private ProductsRepository productsRepository;

    @Value(value="${twilio.account.sid}")
    private String accountSid;

    @Value(value="${twilio.auth.token}")
    private String authToken;

    @Value(value="${twilio.whatsapp.number}")
    private String whatsAppNumber;

    @PostConstruct
    public void init() {
        Twilio.init(this.accountSid, this.authToken);
    }

    public void sendWhatsAppMessage(String toNumber, Orders orders) {
        try {
            String formattedNumber = formatNumber(toNumber);
            String messageBody = buildOrderMessage(orders);

            Message.creator(
                    new PhoneNumber("whatsapp:" + formattedNumber),
                    new PhoneNumber("whatsapp:" + this.whatsAppNumber),
                    messageBody
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("WhatsApp failed: " + e.getMessage());
        }
    }

    public void sendPaymentFailedMessage(String toNumber, RequestOrdersDto requestOrdersDto) {
        try {
            String formattedNumber = formatNumber(toNumber);
            String messageBody = paymentFailedMsg(requestOrdersDto);

            Message.creator(
                    new PhoneNumber("whatsapp:" + formattedNumber),
                    new PhoneNumber("whatsapp:" + this.whatsAppNumber),
                    messageBody
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("WhatsApp failed: " + e.getMessage());
        }
    }

    private String formatNumber(String number) {
        if (number.startsWith("+")) {
            return number;
        }
        return "+91" + number;
    }

    private String buildOrderMessage(Orders order) {
        Products product = this.productsRepository
                .findById(order.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return "Order Confirmed\n"
                + "----------------------------\n"
                + "Order ID   : " + order.getId() + "\n"
                + "Product    : " + product.getProductName() + "\n"
                + "Quantity   : " + order.getQuantity() + "\n"
                + "Subtotal   : Rs." + order.getTotal() + "\n"
                + "GST        : Rs." + order.getGstOnTotal() + "\n"
                + "----------------------------\n"
                + "Total      : Rs." + order.getTotalAmount();
    }

    private String paymentFailedMsg(RequestOrdersDto requestOrdersDto) {
        Products product = this.productsRepository
                .findById(requestOrdersDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return "Payment Failed\n"
                    + "----------------------------\n"
                + "Product : " + product.getProductName() + " (ID: " + product.getId() + ")\n"
                + "Quantity: " + requestOrdersDto.getQuantity() + "\n"
                + "Mobile  : " + requestOrdersDto.getMobileNumber() + "\n"
                + "----------------------------\n"
                + "Please try again.";
    }
}
