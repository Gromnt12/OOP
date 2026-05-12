package ru.nsu.bukhanov.core;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.config.PizzeriaConfig;
import ru.nsu.bukhanov.model.Order;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PizzeriaTest {
    @Test
    void testPizzeriaLifecycle() throws InterruptedException {
        PizzeriaConfig config = new PizzeriaConfig();
        config.bakersSpeeds = List.of(10);
        config.couriersCapacities = List.of(2);
        config.warehouseCapacity = 5;

        Pizzeria pizzeria = new Pizzeria(config);

        // Проверяем старт
        assertDoesNotThrow(pizzeria::start);

        // Проверяем добавление
        pizzeria.addOrder(new Order(1));
        pizzeria.addOrder(new Order(2));

        Thread.sleep(100);
        assertDoesNotThrow(pizzeria::stopGracefully);
    }
}