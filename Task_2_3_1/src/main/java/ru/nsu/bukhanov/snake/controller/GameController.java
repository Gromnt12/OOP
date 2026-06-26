package ru.nsu.bukhanov.snake.controller;

import ru.nsu.bukhanov.snake.model.Direction;
import ru.nsu.bukhanov.snake.model.Game;
import ru.nsu.bukhanov.snake.model.food.FastFood;
import ru.nsu.bukhanov.snake.model.food.SlowFood;
import ru.nsu.bukhanov.snake.model.food.Food;
import ru.nsu.bukhanov.snake.view.GameView;

public class GameController {
    public static final String ACTION_UP = "UP";
    public static final String ACTION_DOWN = "DOWN";
    public static final String ACTION_LEFT = "LEFT";
    public static final String ACTION_RIGHT = "RIGHT";
    public static final String ACTION_RESTART = "RESTART";

    public static final String FOOD_FAST = "FAST";
    public static final String FOOD_SLOW = "SLOW";
    public static final String FOOD_APPLE = "APPLE";

    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static final int FOOD_COUNT = 3;
    private static final int WIN_LENGTH = 15;

    private Game game;
    private final GameView view;

    public GameController(GameView view) {
        this.view = view;
        this.game = new Game(BOARD_WIDTH, BOARD_HEIGHT, FOOD_COUNT, WIN_LENGTH);
    }

    public void update() {
        game.update();
    }

    public double getSpeedMultiplier() {
        return game.getSpeedMultiplier();
    }

    public void handleInput(String action) {
        if (game.isGameOver() || game.isGameWon()) {
            if (ACTION_RESTART.equals(action)) {
                game = new Game(BOARD_WIDTH, BOARD_HEIGHT, FOOD_COUNT, WIN_LENGTH);
                render();
            }
            return;
        }

        boolean validKey = false;
        switch (action) {
            case ACTION_UP -> { game.getSnake().setDirection(Direction.UP); validKey = true; }
            case ACTION_DOWN -> { game.getSnake().setDirection(Direction.DOWN); validKey = true; }
            case ACTION_LEFT -> { game.getSnake().setDirection(Direction.LEFT); validKey = true; }
            case ACTION_RIGHT -> { game.getSnake().setDirection(Direction.RIGHT); validKey = true; }
        }

        if (validKey && !game.isStarted()) {
            game.start();
        }
    }

    public void render() {
        view.drawBackground(game.getWidth(), game.getHeight());
        view.drawObstacles(game.getObstacles());

        for (Food food : game.getFoods()) {
            String type = FOOD_APPLE;
            if (food instanceof FastFood) type = FOOD_FAST;
            else if (food instanceof SlowFood) type = FOOD_SLOW;

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