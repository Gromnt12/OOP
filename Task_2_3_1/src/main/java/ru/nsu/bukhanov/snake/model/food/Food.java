package ru.nsu.bukhanov.snake.model.food;

import ru.nsu.bukhanov.snake.model.Point;
import ru.nsu.bukhanov.snake.model.Snake;
import ru.nsu.bukhanov.snake.model.Game;

public interface Food {
    Point getPosition();
    void applyEffect(Snake snake, Game game);
}