package org.example;

import org.example.comission.CardCommissionStrategy;
import org.example.comission.CashCommissionStrategy;
import org.example.comission.CommissionFactory;
import org.example.comission.CommissionStrategy;
import org.example.comission.PaymentType;
import org.example.comission.TransferCommissionStrategy;
import org.example.discount.CustomerType;
import org.example.discount.DiscountFactory;
import org.example.discount.DiscountStrategy;
import org.example.discount.PremiumDiscountStrategy;
import org.example.discount.RegularDiscountStrategy;
import org.example.discount.VipDiscountStrategy;
import org.example.receipt.ReceiptService;
import org.example.receipt.SampleReceiptService;
import org.example.repository.OrderRepository;
import org.example.repository.SampleOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 Контекст: Тебе достался в наследство код от предыдущего разработчика — сервис обработки заказов интернет-магазина OrderService.
 Он работает, тесты (если бы они были) прошли бы, заказчик доволен... пока не попросил добавить новый тип клиента или способ оплаты.
 Тут выясняется, что каждое такое "простое" изменение требует лезть в один и тот же метод на 80 строк, где всё завязано друг на друга,
 и есть риск что-то сломать в соседней логике.
 Твоя задача: не переписать сервис с нуля, а отрефакторить — сохранив то же поведение (те же скидки, та же комиссия, тот же чек)
 Опирайся на SOLID и паттерны проектирования
Lombok использовать нельзя, все писать руками.
 */
public class OrderService {

    private final CommissionFactory commissionFactory;
    private final DiscountFactory discountFactory;
    private final ReceiptService receiptService;
    private final OrderRepository orderRepository;
    private final  OrderValidator orderValidator;

    public OrderService(CommissionFactory commissionFactory,
                        DiscountFactory discountFactory,
                        ReceiptService receiptService,
                        OrderRepository orderRepository,
                        OrderValidator orderValidator){
        this.commissionFactory = Objects.requireNonNull(commissionFactory);
        this.discountFactory = Objects.requireNonNull(discountFactory);
        this.orderRepository = Objects.requireNonNull(orderRepository);
        this.receiptService = Objects.requireNonNull(receiptService);
        this.orderValidator = Objects.requireNonNull(orderValidator);
    }

    public double processOrder(Order order) {

        // --- валидация ---
        orderValidator.validate(order);

        // --- расчёт скидки по типу клиента ---
        double discount = discountFactory.getDiscountStrategy(order.getCustomerType()).calculateDiscount(order.getAmount());

        // --- расчёт комиссии по способу оплаты ---
        double commission = commissionFactory.getCommissionStrategy(order.getPaymentType()).calculateCommission(order.getAmount());

        // --- итоговая сумма ---
        double finalAmount = order.getAmount() - discount + commission;

        // --- "сохранение" заказа  ---
        /*
            Поменяла местами сохранение заказа и генерацию чека,
            т.к. может произойти ситуация, когда чек уже сформирован,
            а заказ сохранить не удалось
        */
        orderRepository.save(order);

        // --- формирование чека прямо тут ---
        /*
         * Чтобы сервис не нарушал принцип единственной ответственности,
         * нужно перенести логику генерации чеков в другой сервис.
         * Здесь представлен синхронный вариант, но я бы сделала его асинхронным
         * с помощью Outbox pattern
         */
        receiptService.generateReceipt(order, discount, commission, finalAmount);

        return finalAmount;
    }




    public static void main(String[] args) {
        List<CommissionStrategy> commissionStrategies = new ArrayList<>(
                List.of(new CashCommissionStrategy(), new CardCommissionStrategy(), new TransferCommissionStrategy())
        );

        List<DiscountStrategy> discountStrategies = new ArrayList<>(
                List.of(new RegularDiscountStrategy(), new VipDiscountStrategy(), new PremiumDiscountStrategy())
        );

        CommissionFactory commissionFactory = new CommissionFactory(commissionStrategies);
        DiscountFactory discountFactory = new DiscountFactory(discountStrategies);
        OrderRepository orderRepository = new SampleOrderRepository();
        ReceiptService receiptService = new SampleReceiptService();

        OrderService service = new OrderService(
                commissionFactory,
                discountFactory,
                receiptService,
                orderRepository,
                new OrderValidator());

        Order order1 = new Order("1", CustomerType.VIP, PaymentType.CARD, List.of("Laptop", "Mouse"), 1000.0);
        Order order2 = new Order("2", CustomerType.REGULAR, PaymentType.CASH, List.of("Book"), 20.0);

        System.out.println("Final amount 1: " + service.processOrder(order1));
        System.out.println("Final amount 2: " + service.processOrder(order2));
    }

    /*
        SRP
        OCP
        LSP
        ISP
        DIP
     */
}

