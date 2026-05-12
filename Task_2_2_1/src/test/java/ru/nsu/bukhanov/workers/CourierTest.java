package ru.nsu.bukhanov.workers;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.storage.Warehouse;

class CourierTest {
    @Test
    void testCourierRunAndInterrupt() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        warehouse.put(new Order(1));

        Courier courier = new Courier(2, warehouse);
        Thread thread = new Thread(courier);
        thread.start();

        Thread.sleep(50);
        thread.interrupt();
        thread.join();
    }
}