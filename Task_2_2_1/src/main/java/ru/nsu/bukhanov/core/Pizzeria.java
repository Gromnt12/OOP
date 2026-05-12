package ru.nsu.bukhanov.core;

import ru.nsu.bukhanov.config.PizzeriaConfig;
import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.storage.CustomQueue;
import ru.nsu.bukhanov.storage.Warehouse;
import ru.nsu.bukhanov.workers.Baker;
import ru.nsu.bukhanov.workers.Courier;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private final CustomQueue orderQueue;
    private final Warehouse warehouse;
    private final List<Thread> bakerThreads = new ArrayList<>();
    private final List<Thread> courierThreads = new ArrayList<>();

    public Pizzeria(PizzeriaConfig config) {
        this.orderQueue = new CustomQueue();
        this.warehouse = new Warehouse(config.warehouseCapacity);

        for (int speed : config.bakersSpeeds) {
            bakerThreads.add(new Thread(new Baker(speed, orderQueue, warehouse)));
        }
        for (int capacity : config.couriersCapacities) {
            courierThreads.add(new Thread(new Courier(capacity, warehouse)));
        }
    }

    public void start() {
        bakerThreads.forEach(Thread::start);
        courierThreads.forEach(Thread::start);
    }

    public void addOrder(Order order) {
        orderQueue.put(order);
    }

    public void stopGracefully() {
        orderQueue.stopAccepting(); // Перестаем принимать новые заказы

        // Ждем пока пекари доделают пиццы из очереди
        for (Thread baker : bakerThreads) {
            try {
                baker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (Thread courier : courierThreads) {
            courier.interrupt();
        }
    }
}