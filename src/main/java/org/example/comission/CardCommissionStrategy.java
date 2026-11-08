package org.example.comission;

public class CardCommissionStrategy implements CommissionStrategy {
    @Override
    public PaymentType getPaymentType() {
        return PaymentType.CARD;
    }

    @Override
    public double calculateCommission(double amount) {
        return amount * 0.02;
    }
}
