package org.example.comission;

import java.math.BigDecimal;

public class CashCommissionStrategy implements CommissionStrategy {
    private static final BigDecimal COMMISSION_RATE = BigDecimal.ZERO;

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CASH;
    }

    @Override
    public BigDecimal calculateCommission(BigDecimal amount) {
        return amount.multiply(COMMISSION_RATE);
    }
}
