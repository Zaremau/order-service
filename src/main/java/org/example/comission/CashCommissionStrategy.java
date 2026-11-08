package org.example.comission;

public class CashCommissionStrategy implements CommissionStrategy {
    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CASH;
    }

    @Override
    public double calculateCommission(double amount) {
        return amount * 0.0;
    }
}
