package org.example.discount;

public class RegularDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.0;
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.REGULAR;
    }
}
