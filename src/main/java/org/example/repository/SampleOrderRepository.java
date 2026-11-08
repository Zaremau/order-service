package org.example.repository;

import org.example.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SampleOrderRepository implements OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        Objects.requireNonNull(order, "order");
        orders.add(order);
    }
}
