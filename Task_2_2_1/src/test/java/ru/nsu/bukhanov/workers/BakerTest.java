package ru.nsu.bukhanov.workers;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.model.Order;
import ru.nsu.bukhanov.storage.CustomQueue;
import ru.nsu.bukhanov.storage.Warehouse;

class BakerTest {
    @Test
    void testBakerRunAndInterrupt() throws InterruptedException {
        CustomQueue queue = new CustomQueue();
        Warehouse warehouse = new Warehouse(5);
        queue.put(new Order(1));

        Baker baker = new Baker(10, queue, warehouse);
        Thread thread = new Thread(baker);
        thread.start();

        Thread.sleep(50);
        thread.interrupt();
        thread.join();
    }
}