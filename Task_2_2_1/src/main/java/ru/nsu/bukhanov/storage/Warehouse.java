package ru.nsu.bukhanov.storage;

import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.model.OrderState;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Реализация склада без использования готовых потокобезопасных структур
public class Warehouse {
    private final int capacity;
    private final Queue<Order> pizzas = new LinkedList<>();
    private volatile boolean running = true;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(Order order) throws InterruptedException {
        while (pizzas.size() >= capacity) {
            wait(); // Пекарь ожидает, когда освободится место
        }
        pizzas.add(order);
        order.setState(OrderState.READY);
        notifyAll();
    }

    public synchronized List<Order> take(int maxVolume) throws InterruptedException {
        while (running && pizzas.isEmpty()) {
            wait(); // Курьер ожидает появления готовых пицц
        }
        List<Order> batch = new ArrayList<>();
        while (!pizzas.isEmpty() && batch.size() < maxVolume) {
            batch.add(pizzas.poll());
        }
        notifyAll();
        return batch;
    }

    public synchronized void finish() {
        running = false;
        notifyAll();
    }
}