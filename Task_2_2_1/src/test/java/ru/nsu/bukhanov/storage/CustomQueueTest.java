package ru.nsu.bukhanov.storage;

import ru.nsu.bukhanov.model.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomQueueTest {
    @Test
    void testPutAndTake() throws InterruptedException {
        CustomQueue queue = new CustomQueue();
        Order order = new Order(1);

        queue.put(order);
        Order taken = queue.take();

        assertEquals(order, taken);
    }

    @Test
    void testStopAccepting() throws InterruptedException {
        CustomQueue queue = new CustomQueue();
        queue.stopAccepting();

        // После stopAccepting новые заказы не должны добавляться
        queue.put(new Order(1));

        // Если очередь пуста, take() в нормальных условиях заблокирует поток.
        // Но при stopAccepting он должен сразу вернуть null.
        Order taken = queue.take();
        assertNull(taken);
    }
}