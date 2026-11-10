package org.example.discount;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(BigDecimal amount);
    CustomerType getCustomerType();
}
