package org.example.comission;

public interface CommissionStrategyProvider {

    CommissionStrategy getCommissionStrategy(PaymentType paymentType);
}
