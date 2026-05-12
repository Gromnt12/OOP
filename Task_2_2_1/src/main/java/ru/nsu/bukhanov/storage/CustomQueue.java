package ru.nsu.bukhanov.storage;

import ru.nsu.bukhanov.model.Order;
import java.util.LinkedList;
import java.util.Queue;

public class CustomQueue {
    private final Queue<Order> orders = new LinkedList<>();
    private boolean closed = false;

    public synchronized void put(Order order) {
        if (closed) {
            return;
        }
        orders.add(order);
        notifyAll();
    }

    public synchronized Order take() throws InterruptedException {
        if (orders.isEmpty() && closed) {
            return null;
        }
        while (orders.isEmpty()) {
            wait();
        }
        return orders.poll();
    }

    public synchronized void stopAccepting() {
        closed = true;
        notifyAll();
    }
}