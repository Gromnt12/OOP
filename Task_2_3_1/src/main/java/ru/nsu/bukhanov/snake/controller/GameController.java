package ru.nsu.bukhanov.snake.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import ru.nsu.bukhanov.snake.model.Direction;
import ru.nsu.bukhanov.snake.model.Game;
import ru.nsu.bukhanov.snake.view.GameRenderer;

public class GameController {
    @FXML
    private Canvas gameCanvas;

    private Game game;
    private GameRenderer renderer;
    private static final int CELL_SIZE = 30;

    private static final long BASE_UPDATE_INTERVAL = 150_000_000;
    private long lastUpdate = 0;

    public void initialize() {
        game = new Game(20, 20, 3, 15);
        renderer = new GameRenderer(gameCanvas.getGraphicsContext2D(), CELL_SIZE);

        renderer.render(game);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentInterval = (long) (BASE_UPDATE_INTERVAL / game.getSpeedMultiplier());

                if (now - lastUpdate >= currentInterval) {
                    game.update();
                    renderer.render(game);
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    public void handleInput(KeyCode code) {
        if (game.isGameOver() || game.isGameWon()) {
            if (code == KeyCode.R) {
                game = new Game(20, 20, 3, 15);
                renderer.render(game);
            }
            return;
        }

        boolean validKey = false;

        switch (code) {
            case W, UP -> { game.getSnake().setDirection(Direction.UP); validKey = true; }
            case S, DOWN -> { game.getSnake().setDirection(Direction.DOWN); validKey = true; }
            case A, LEFT -> { game.getSnake().setDirection(Direction.LEFT); validKey = true; }
            case D, RIGHT -> { game.getSnake().setDirection(Direction.RIGHT); validKey = true; }
        }

        if (validKey && !game.isStarted()) {
            game.start();
        }
    }
}