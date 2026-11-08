package org.example.discount;

public class VipDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.05;
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.VIP;
    }
}
