package ru.nsu.bukhanov.workers;

import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.model.OrderState;
import ru.nsu.bukhanov.storage.CustomQueue;
import ru.nsu.bukhanov.storage.Warehouse;

public class Baker implements Runnable {
    private final int speed;
    private final CustomQueue orderQueue;
    private final Warehouse warehouse;

    public Baker(int speed, CustomQueue orderQueue, Warehouse warehouse) {
        this.speed = speed;
        this.orderQueue = orderQueue;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = orderQueue.take();
                if (order == null) break;

                order.setState(OrderState.BAKING);
                Thread.sleep(speed);
                warehouse.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}