package org.example.discount;

import java.math.BigDecimal;

public class VipDiscountStrategy implements DiscountStrategy{
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.05");

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(DISCOUNT_RATE);
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.VIP;
    }
}
