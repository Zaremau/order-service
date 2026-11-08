package org.example.comission;

public class TransferCommissionStrategy implements CommissionStrategy {
    @Override
    public PaymentType getPaymentType() {
        return PaymentType.TRANSFER;
    }

    @Override
    public double calculateCommission(double amount) {
        return amount * 0.01;
    }
}
