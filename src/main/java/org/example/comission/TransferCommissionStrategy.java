package org.example.comission;

import java.math.BigDecimal;

public class TransferCommissionStrategy implements CommissionStrategy {
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.01");

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.TRANSFER;
    }

    @Override
    public BigDecimal calculateCommission(BigDecimal amount) {
        return amount.multiply(COMMISSION_RATE);
    }
}
