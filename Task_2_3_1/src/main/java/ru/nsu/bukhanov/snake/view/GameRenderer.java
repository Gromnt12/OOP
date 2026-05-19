package ru.nsu.bukhanov.snake.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.bukhanov.snake.model.Game;
import ru.nsu.bukhanov.snake.model.Point;
import ru.nsu.bukhanov.snake.model.food.Apple;
import ru.nsu.bukhanov.snake.model.food.FastFood;
import ru.nsu.bukhanov.snake.model.food.SlowFood;
import ru.nsu.bukhanov.snake.model.food.Food;

public class GameRenderer {
    private final GraphicsContext gc;
    private final int cellSize;

    public GameRenderer(GraphicsContext gc, int cellSize) {
        this.gc = gc;
        this.cellSize = cellSize;
        this.gc.setFont(new Font("Arial", 18));
    }

    public void render(Game game) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, game.getWidth() * cellSize, game.getHeight() * cellSize);

        gc.setFill(Color.DARKGRAY);
        for (Point obs : game.getObstacles()) {
            gc.fillRect(obs.x * cellSize, obs.y * cellSize, cellSize, cellSize);
        }

        for (Food food : game.getFoods()) {
            if (food instanceof FastFood) {
                gc.setFill(Color.YELLOW);
            } else if (food instanceof SlowFood) {
                gc.setFill(Color.BLUE);
            } else {
                gc.setFill(Color.RED);
            }
            gc.fillRect(food.getPosition().x * cellSize, food.getPosition().y * cellSize, cellSize, cellSize);
        }

        gc.setFill(Color.GREEN);
        for (Point p : game.getSnake().getBody()) {
            gc.fillRect(p.x * cellSize, p.y * cellSize, cellSize - 1, cellSize - 1);
        }

        gc.setFill(Color.WHITE);
        gc.fillText("Длина: " + game.getSnake().getBody().size(), 20, 30);
        gc.fillText(String.format("Скорость: %.1fx", game.getSpeedMultiplier()), 20, 55);

        if (!game.isStarted()) {
            gc.setFill(Color.WHITE);
            gc.fillText("НАЖМИТЕ WASD ИЛИ СТРЕЛКИ ДЛЯ СТАРТА", 80, game.getHeight() * cellSize / 2.0);
        } else if (game.isGameOver()) {
            gc.setFill(Color.WHITE);
            gc.fillText("ИГРА ОКОНЧЕНА!", 220, game.getHeight() * cellSize / 2.0);
            gc.fillText("Нажмите 'R' для рестарта", 195, game.getHeight() * cellSize / 2.0 + 30);
        } else if (game.isGameWon()) {
            gc.setFill(Color.YELLOW);
            gc.fillText("ВЫ ПОБЕДИЛИ!", 230, game.getHeight() * cellSize / 2.0);
            gc.fillText("Нажмите 'R' для рестарта", 195, game.getHeight() * cellSize / 2.0 + 30);
        }
    }
}