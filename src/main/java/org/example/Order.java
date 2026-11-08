package org.example;

import org.example.comission.PaymentType;
import org.example.discount.CustomerType;

import java.util.List;

public class Order {

    private String id;
    private CustomerType customerType; // "REGULAR", "VIP", "PREMIUM"
    private PaymentType paymentType;  // "CARD", "CASH", "TRANSFER"
    private List<String> items;
    private double amount;

    public Order(String id, CustomerType customerType, PaymentType paymentType, List<String> items, double amount) {
        this.id = id;
        this.customerType = customerType;
        this.paymentType = paymentType;
        this.items = items;
        this.amount = amount;
    }

    public String getId() { return id; }
    public CustomerType getCustomerType() { return customerType; }
    public PaymentType getPaymentType() { return paymentType; }
    public List<String> getItems() { return items; }
    public double getAmount() { return amount; }
}
