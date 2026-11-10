package org.example.receipt;

import org.example.Order;

import java.math.BigDecimal;

public interface ReceiptService {
    void generateReceipt(Order order, BigDecimal discount, BigDecimal commission, BigDecimal finalAmount);
}
