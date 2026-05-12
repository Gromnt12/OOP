package ru.nsu.bukhanov.storage;

import ru.nsu.bukhanov.model.Order;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    @Test
    void testPutAndTakePizzas() throws InterruptedException {
        Warehouse warehouse = new Warehouse(3);
        Order o1 = new Order(1);
        Order o2 = new Order(2);

        warehouse.put(o1);
        warehouse.put(o2);

        List<Order> batch = warehouse.take(2);

        assertEquals(2, batch.size());
        assertTrue(batch.contains(o1));
        assertTrue(batch.contains(o2));
    }

    @Test
    void testTakeRespectsMaxVolume() throws InterruptedException {
        Warehouse warehouse = new Warehouse(5);
        warehouse.put(new Order(1));
        warehouse.put(new Order(2));
        warehouse.put(new Order(3));

        List<Order> batch = warehouse.take(2);
        assertEquals(2, batch.size());
    }
}