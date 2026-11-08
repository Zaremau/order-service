package org.example.discount;

public interface DiscountStrategy {
    double calculateDiscount(double amount);
    CustomerType getCustomerType();
}
