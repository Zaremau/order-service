package org.example.receipt;

import org.example.Order;

public interface ReceiptService {
    void generateReceipt(Order order, double discount, double commission, double finalAmount);
}
