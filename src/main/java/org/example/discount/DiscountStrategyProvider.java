package org.example.discount;

public interface DiscountStrategyProvider {
    DiscountStrategy getDiscountStrategy(CustomerType customerType);
}
