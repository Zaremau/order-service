package org.example.receipt;

import org.example.Order;

import java.math.BigDecimal;

public class SampleReceiptService implements ReceiptService {

    @Override
    public void generateReceipt(Order order, BigDecimal discount, BigDecimal commission, BigDecimal finalAmount) {
        System.out.println("=== RECEIPT ===");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Items: " + order.getItems());
        System.out.println("Base amount: " + order.getAmount());
        System.out.println("Discount: -" + discount);
        System.out.println("Commission: +" + commission);
        System.out.println("Final amount: " + finalAmount);
        System.out.println("================");
    }
}
