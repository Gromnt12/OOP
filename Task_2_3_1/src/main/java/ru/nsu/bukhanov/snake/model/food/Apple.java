package ru.nsu.bukhanov.snake.model.food;

import ru.nsu.bukhanov.snake.model.Point;
import ru.nsu.bukhanov.snake.model.Snake;
import ru.nsu.bukhanov.snake.model.Game;

public class Apple implements Food {
    private final Point position;

    public Apple(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void applyEffect(Snake snake, Game game) {
        snake.grow(1);
    }
}