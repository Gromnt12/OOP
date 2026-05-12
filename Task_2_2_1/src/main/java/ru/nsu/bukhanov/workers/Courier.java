package ru.nsu.bukhanov.workers;

import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.model.OrderState;
import ru.nsu.bukhanov.storage.Warehouse;
import java.util.List;

public class Courier extends Thread {
    private final int trunkCapacity;
    private final Warehouse warehouse;
    private volatile boolean running = true;

    public Courier(int trunkCapacity, Warehouse warehouse) {
        this.trunkCapacity = trunkCapacity;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Order> batch = warehouse.take(trunkCapacity);
                if (batch.isEmpty() && !running) {
                    return;
                }
                for (Order order : batch) {
                    order.setState(OrderState.DELIVERING);
                    Thread.sleep(1000);
                    order.setState(OrderState.DELIVERED);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void finish() {
        running = false;
    }
}