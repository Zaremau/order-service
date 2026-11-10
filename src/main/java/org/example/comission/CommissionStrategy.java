package org.example.comission;

import java.math.BigDecimal;

public interface CommissionStrategy {
    PaymentType getPaymentType();
    BigDecimal calculateCommission(BigDecimal amount);
}
