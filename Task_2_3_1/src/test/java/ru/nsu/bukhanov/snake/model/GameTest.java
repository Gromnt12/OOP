package ru.nsu.bukhanov.snake.model;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.snake.model.food.FastFood;
import ru.nsu.bukhanov.snake.model.food.SlowFood;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testGameInitialization() {
        Game game = new Game(20, 20, 3, 10);
        assertFalse(game.isGameOver());
        assertFalse(game.isGameWon());
        assertFalse(game.isStarted());
        assertEquals(3, game.getFoods().size());
        assertEquals(1.0, game.getSpeedMultiplier());
    }

    @Test
    void testWinCondition() {
        Game game = new Game(20, 20, 1, 2);
        game.start();

        game.getSnake().grow(1);
        game.update();

        assertTrue(game.isGameWon(), "Game should be won when length reaches winLength");
    }

    @Test
    void testSpeedModifiers() {
        Game game = new Game(20, 20, 0, 10);


        FastFood fast = new FastFood(new Point(0, 0));
        fast.applyEffect(game.getSnake(), game);
        assertEquals(1.5, game.getSpeedMultiplier(), 0.001);

        SlowFood slow = new SlowFood(new Point(0, 0));
        slow.applyEffect(game.getSnake(), game);
        assertEquals(1.0, game.getSpeedMultiplier(), 0.001);
    }

    @Test
    void testGameOverOnCollision() {
        Game game = new Game(20, 20, 0, 10);
        game.start();

        // Змейка стартует в центре (10,10). Идем в стену.
        game.getSnake().setDirection(Direction.RIGHT);
        for (int i = 0; i < 11; i++) {
            game.update();
        }
        assertTrue(game.isGameOver());
    }
}