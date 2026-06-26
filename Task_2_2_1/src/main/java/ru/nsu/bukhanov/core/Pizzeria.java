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
    private final List<Baker> bakers = new ArrayList<>();
    private final List<Courier> couriers = new ArrayList<>();

    public Pizzeria(PizzeriaConfig config) {
        this.orderQueue = new CustomQueue();
        warehouse = new Warehouse(config.warehouseCapacity);

        for (int speed : config.bakersSpeeds) {
            bakers.add(new Baker(speed, orderQueue, warehouse));
        }
        for (int capacity : config.couriersCapacities) {
            couriers.add(new Courier(capacity, warehouse));
        }
    }

    public void start() {
        bakers.forEach(Thread::start);
        couriers.forEach(Thread::start);
    }

    public void addOrder(Order order) {
        orderQueue.put(order);
    }

    public void stopGracefully() {
        orderQueue.stopAccepting(); // Перестаем принимать новые заказы

        for (Baker baker : bakers) {
            baker.finish();
        }

        // Ждем пока пекари доделают пиццы из очереди
        for (Baker baker : bakers) {
            try {
                baker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        warehouse.finish();

        for (Courier courier : couriers) {
            courier.finish();
        }

        for (Courier courie : couriers) {
            try {
                courie.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}