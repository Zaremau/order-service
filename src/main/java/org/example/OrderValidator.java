package org.example;

public class OrderValidator {

    public void validate(Order order) {
        if (order.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Order amount must be positive");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
    }
}
