package org.example.discount;

public class PremiumDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.PREMIUM;
    }
}
