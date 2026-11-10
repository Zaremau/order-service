package org.example.discount;

import java.math.BigDecimal;

public class PremiumDiscountStrategy implements DiscountStrategy{
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(DISCOUNT_RATE);
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.PREMIUM;
    }
}
