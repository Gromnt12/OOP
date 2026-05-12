package ru.nsu.bukhanov.workers;

import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.model.OrderState;
import ru.nsu.bukhanov.storage.Warehouse;
import java.util.List;

public class Courier implements Runnable {
    private final int trunkCapacity;
    private final Warehouse warehouse;

    public Courier(int trunkCapacity, Warehouse warehouse) {
        this.trunkCapacity = trunkCapacity;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<Order> batch = warehouse.take(trunkCapacity);
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
}