package ru.nsu.bukhanov.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void testOrderCreationAndStateChange() {
        Order order = new Order(1);
        assertEquals(1, order.getId());

        // Проверяем, что смена состояний не вызывает ошибок
        assertDoesNotThrow(() -> {
            order.setState(OrderState.BAKING);
            order.setState(OrderState.READY);
            order.setState(OrderState.DELIVERED);
        });
    }
}