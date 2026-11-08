package org.example.comission;

public interface CommissionStrategy {
    PaymentType getPaymentType();
    double calculateCommission(double amount);
}
