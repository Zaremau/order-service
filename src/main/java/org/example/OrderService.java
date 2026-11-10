package org.example;

import org.example.comission.*;
import org.example.discount.*;
import org.example.receipt.ReceiptService;
import org.example.receipt.SampleReceiptService;
import org.example.repository.OrderRepository;
import org.example.repository.SampleOrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderService {

    private final CommissionStrategyProvider commissionStrategyProvider;
    private final DiscountStrategyProvider discountStrategyProvider;
    private final ReceiptService receiptService;
    private final OrderRepository orderRepository;
    private final  OrderValidator orderValidator;

    public OrderService(CommissionStrategyProvider commissionStrategyProvider,
                        DiscountStrategyProvider discountStrategyProvider,
                        ReceiptService receiptService,
                        OrderRepository orderRepository,
                        OrderValidator orderValidator){
        this.commissionStrategyProvider = Objects.requireNonNull(commissionStrategyProvider);
        this.discountStrategyProvider = Objects.requireNonNull(discountStrategyProvider);
        this.orderRepository = Objects.requireNonNull(orderRepository);
        this.receiptService = Objects.requireNonNull(receiptService);
        this.orderValidator = Objects.requireNonNull(orderValidator);
    }

    public BigDecimal processOrder(Order order) {

        // --- валидация ---
        orderValidator.validate(order);

        // --- расчёт скидки по типу клиента ---
        BigDecimal discount = discountStrategyProvider.getDiscountStrategy(order.getCustomerType()).calculateDiscount(order.getAmount());

        // --- расчёт комиссии по способу оплаты ---
        BigDecimal commission = commissionStrategyProvider.getCommissionStrategy(order.getPaymentType()).calculateCommission(order.getAmount());

        // --- итоговая сумма ---
        BigDecimal finalAmount = order.getAmount().subtract(discount).add(commission);

        // --- "сохранение" заказа  ---
        orderRepository.save(order);

        // --- формирование чека прямо тут ---
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

        CommissionStrategyProvider commissionStrategyProvider = new CommissionFactory(commissionStrategies);
        DiscountStrategyProvider discountStrategyProvider = new DiscountFactory(discountStrategies);
        OrderRepository orderRepository = new SampleOrderRepository();
        ReceiptService receiptService = new SampleReceiptService();

        OrderService service = new OrderService(
                commissionStrategyProvider,
                discountStrategyProvider,
                receiptService,
                orderRepository,
                new OrderValidator());

        Order order1 = new Order("1", CustomerType.VIP, PaymentType.CARD, List.of("Laptop", "Mouse"), new BigDecimal("1000.0"));
        Order order2 = new Order("2", CustomerType.REGULAR, PaymentType.CASH, List.of("Book"), new BigDecimal("20.0"));

        System.out.println("Final amount 1: " + service.processOrder(order1));
        System.out.println("Final amount 2: " + service.processOrder(order2));
    }

    /*
        SRP: В исходном коде processOrder совмещал валидацию, расчёт скидки и комиссии, генерацию чека и сохранение заказа.
        По SRP у класса должна быть одна причина изменения, поэтому я вынесла эти ответственности в OrderValidator, стратегии, ReceiptService и OrderRepository.
        Теперь OrderService занимается только оркестрацией. Расчёт итоговой суммы оставила в сервисе - это простая формула, выносить её было бы избыточно.

        OCP: В исходном коде при добавлении нового типа клиента или способа оплаты приходилось бы менять processOrder.
        Я применила Strategy и Factory Method (реестр стратегий): новая стратегия добавляется новым классом и регистрацией в фабрике,
         без изменения существующего кода.

        LSP: Все реализации CommissionStrategy, DiscountStrategy, ReceiptService и OrderRepository взаимозаменяемы:
        любую можно подставить вместо другой без нарушения работы OrderService.
        Контракты интерфейсов соблюдены, ни одна реализация не усиливает предусловия и не ослабляет постусловия.
        Например, CardCommissionStrategy и CashCommissionStrategy можно поменять местами, и сервис продолжит работать корректно


        ISP: В исходном коде интерфейсов не было, всё было зашито в одном классе.
        Я разделила обязанности на узкие интерфейсы: CommissionStrategy, CommissionStrategyProvider, DiscountStrategy, DiscountStrategyProvider, ReceiptService, OrderRepository.
        Каждый клиент зависит только от тех методов, которые реально использует.
        Например, OrderService не видит деталей хранения в OrderRepository.

        DIP: В исходном коде сервис зависел от конкретной реализации хранения заказов.
        По DIP модули высокого уровня должны зависеть от абстракций, поэтому я ввела интерфейсы-провайдеры и сделала так,
        чтобы OrderService зависел от них, а не от конкретных CommissionFactory и SampleOrderRepository.
         Реализации подставляются через конструктор, что снижает связанность и упрощает тестирование с stub-объектами

        Я поменяла местами сохранение заказа и генерацию чека: если чек упадёт, заказ уже сохранён и не потеряется.
        В реальном приложении генерацию чека я бы сделала асинхронной через Outbox pattern: заказ и событие генерации чека сохраняются в одной транзакции, а фоновый процесс формирует чек.
         Это гарантирует at-least-once доставку даже при сбое сервиса чеков.


     */
}

