package org.example.discount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DiscountFactory {
    private final Map<CustomerType, DiscountStrategy> discountStrategyHashMap = new HashMap<>();

    public DiscountFactory(List<DiscountStrategy> strategies){
        Objects.requireNonNull(strategies);
        for(DiscountStrategy strategy: strategies){
            discountStrategyHashMap.put(strategy.getCustomerType(), strategy);
        }
    }

    public DiscountStrategy getDiscountStrategy(CustomerType customerType){
        return Optional.ofNullable(discountStrategyHashMap.get(customerType))
                .orElseThrow(() -> new IllegalArgumentException("Unknown customer type: " + customerType));
    }
}
