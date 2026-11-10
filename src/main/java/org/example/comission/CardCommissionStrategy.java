package org.example.comission;

import java.math.BigDecimal;

public class CardCommissionStrategy implements CommissionStrategy {

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.02");

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CARD;
    }

    @Override
    public BigDecimal calculateCommission(BigDecimal amount) {
        return amount.multiply(COMMISSION_RATE);
    }
}
