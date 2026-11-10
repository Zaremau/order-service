package org.example.discount;

import java.math.BigDecimal;

public class RegularDiscountStrategy implements DiscountStrategy{
    private static final BigDecimal DISCOUNT_RATE = BigDecimal.ZERO;

    @Override
    public BigDecimal calculateDiscount(BigDecimal amount) {
        return amount.multiply(DISCOUNT_RATE);
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.REGULAR;
    }
}
