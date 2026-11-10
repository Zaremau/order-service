package org.example.comission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class CommissionFactory implements CommissionStrategyProvider{

    private final Map<PaymentType, CommissionStrategy> commissionStrategyHashMap = new HashMap<>();

    public CommissionFactory(List<CommissionStrategy> strategies){
        Objects.requireNonNull(strategies, "strategies");

        for(CommissionStrategy strategy : strategies){
            commissionStrategyHashMap.put(strategy.getPaymentType(), strategy);
        }
    }

    @Override
    public CommissionStrategy getCommissionStrategy(PaymentType paymentType){
        return Optional.ofNullable(commissionStrategyHashMap.get(paymentType))
                .orElseThrow(() -> new IllegalArgumentException("Unknown payment type: " + paymentType));
    }
}
