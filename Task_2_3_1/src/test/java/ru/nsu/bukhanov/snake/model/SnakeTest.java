package ru.nsu.bukhanov.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(new Point(10, 10));
    }

    @Test
    void testInitialState() {
        assertEquals(1, snake.getBody().size());
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void testMovementAndGrowth() {
        snake.grow(2);
        snake.move();
        snake.move();

        assertEquals(3, snake.getBody().size());
        assertEquals(new Point(12, 10), snake.getBody().getFirst());
    }

    @Test
    void testDirectionChangeConstraints() {
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.RIGHT, snake.getDirection());

        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, snake.getDirection());
    }

    @Test
    void testWallCollision() {
        snake = new Snake(new Point(19, 10));
        snake.setDirection(Direction.RIGHT);

        assertTrue(snake.willCollide(20, 20, java.util.Collections.emptyList()));
    }

    @Test
    void testSelfCollision() {
        snake.grow(4);
        snake.move();
        snake.setDirection(Direction.DOWN);
        snake.move();
        snake.setDirection(Direction.LEFT);
        snake.move();
        snake.setDirection(Direction.UP);
        assertTrue(snake.willCollide(20, 20, java.util.Collections.emptyList()));
    }
}