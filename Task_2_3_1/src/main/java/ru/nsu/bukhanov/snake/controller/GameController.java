package ru.nsu.bukhanov.snake.controller;

import ru.nsu.bukhanov.snake.model.Direction;
import ru.nsu.bukhanov.snake.model.Game;
import ru.nsu.bukhanov.snake.model.food.FastFood;
import ru.nsu.bukhanov.snake.model.food.SlowFood;
import ru.nsu.bukhanov.snake.model.food.Food;
import ru.nsu.bukhanov.snake.view.GameView;

public class GameController {
    private Game game;
    private final GameView view;

    public GameController(GameView view) {
        this.view = view;
        this.game = new Game(20, 20, 3, 15);
    }

    public void update() {
        game.update();
    }

    public double getSpeedMultiplier() {
        return game.getSpeedMultiplier();
    }

    public void handleInput(String action) {
        if (game.isGameOver() || game.isGameWon()) {
            if ("RESTART".equals(action)) {
                game = new Game(20, 20, 3, 15);
                render();
            }
            return;
        }

        boolean validKey = false;
        switch (action) {
            case "UP" -> { game.getSnake().setDirection(Direction.UP); validKey = true; }
            case "DOWN" -> { game.getSnake().setDirection(Direction.DOWN); validKey = true; }
            case "LEFT" -> { game.getSnake().setDirection(Direction.LEFT); validKey = true; }
            case "RIGHT" -> { game.getSnake().setDirection(Direction.RIGHT); validKey = true; }
        }

        if (validKey && !game.isStarted()) {
            game.start();
        }
    }
    public void render() {
        view.drawBackground(game.getWidth(), game.getHeight());
        view.drawObstacles(game.getObstacles());

        for (Food food : game.getFoods()) {
            String type = "APPLE";
            if (food instanceof FastFood) type = "FAST";
            else if (food instanceof SlowFood) type = "SLOW";

            view.drawFood(food.getPosition(), type);
        }

        view.drawSnake(game.getSnake().getBody());

        view.drawHUD(
                game.getSnake().getBody().size(),
                game.getSpeedMultiplier(),
                game.isStarted(),
                game.isGameOver(),
                game.isGameWon(),
                game.getWidth(),
                game.getHeight()
        );
    }
}