package ru.nsu.bukhanov.storage;

import ru.nsu.bukhanov.model.Order;
import java.util.LinkedList;
import java.util.Queue;

public class CustomQueue {
    private final Queue<Order> orders = new LinkedList<>();
    private boolean isAccepting = true;

    public synchronized void put(Order order) {
        if (isAccepting) {
            orders.add(order);
            notifyAll();
        }
    }

    public synchronized Order take() throws InterruptedException {
        while (orders.isEmpty() && isAccepting) {
            wait();
        }
        return orders.poll();
    }

    public synchronized void stopAccepting() {
        isAccepting = false;
        notifyAll();
    }
}