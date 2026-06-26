package ru.nsu.bukhanov.snake.view;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.bukhanov.snake.controller.GameController;
import ru.nsu.bukhanov.snake.model.Point;

import java.util.List;

public class GameView {
    private static final int CELL_SIZE = 30;
    private static final long BASE_UPDATE_INTERVAL = 150_000_000;
    private static final String FONT_NAME = "Arial";
    private static final int FONT_SIZE = 18;

    private static final String TEXT_LENGTH = "Длина: ";
    private static final String TEXT_SPEED = "Скорость: %.1fx";
    private static final String TEXT_START = "НАЖМИТЕ WASD ИЛИ СТРЕЛКИ ДЛЯ СТАРТА";
    private static final String TEXT_GAME_OVER = "ИГРА ОКОНЧЕНА!";
    private static final String TEXT_WIN = "ВЫ ПОБЕДИЛИ!";
    private static final String TEXT_RESTART = "Нажмите 'R' для рестарта";

    private static final int HUD_INFO_X = 20;
    private static final int HUD_LENGTH_Y = 30;
    private static final int HUD_SPEED_Y = 55;
    private static final int MSG_START_X = 80;
    private static final int MSG_GAME_OVER_X = 220;
    private static final int MSG_WIN_X = 230;
    private static final int MSG_RESTART_X = 195;
    private static final int MSG_RESTART_OFFSET_Y = 30;

    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;

    private GameController controller;
    private long lastUpdate = 0;

    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        gc.setFont(new Font(FONT_NAME, FONT_SIZE));
        controller = new GameController(this);
        controller.render();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentInterval = (long) (BASE_UPDATE_INTERVAL / controller.getSpeedMultiplier());
                if (now - lastUpdate >= currentInterval) {
                    controller.update();
                    controller.render();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    public void handleInput(KeyCode code) {
        switch (code) {
            case W, UP -> controller.handleInput(GameController.ACTION_UP);
            case S, DOWN -> controller.handleInput(GameController.ACTION_DOWN);
            case A, LEFT -> controller.handleInput(GameController.ACTION_LEFT);
            case D, RIGHT -> controller.handleInput(GameController.ACTION_RIGHT);
            case R -> controller.handleInput(GameController.ACTION_RESTART);
        }
    }

    public void drawBackground(int width, int height) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
    }

    public void drawObstacles(List<Point> obstacles) {
        gc.setFill(Color.DARKGRAY);
        for (Point obs : obstacles) {
            gc.fillRect(obs.x * CELL_SIZE, obs.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public void drawFood(Point position, String type) {
        switch (type) {
            case GameController.FOOD_FAST -> gc.setFill(Color.YELLOW);
            case GameController.FOOD_SLOW -> gc.setFill(Color.BLUE);
            default -> gc.setFill(Color.RED);
        }
        gc.fillRect(position.x * CELL_SIZE, position.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public void drawSnake(List<Point> body) {
        gc.setFill(Color.GREEN);
        for (Point p : body) {
            gc.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE - 1, CELL_SIZE - 1);
        }
    }

    public void drawHUD(int length, double speed, boolean started, boolean gameOver, boolean won, int width, int height) {
        gc.setFill(Color.WHITE);
        gc.fillText(TEXT_LENGTH + length, HUD_INFO_X, HUD_LENGTH_Y);
        gc.fillText(String.format(TEXT_SPEED, speed), HUD_INFO_X, HUD_SPEED_Y);

        double centerY = height * CELL_SIZE / 2.0;

        if (!started) {
            gc.setFill(Color.WHITE);
            gc.fillText(TEXT_START, MSG_START_X, centerY);
        } else if (gameOver) {
            gc.setFill(Color.WHITE);
            gc.fillText(TEXT_GAME_OVER, MSG_GAME_OVER_X, centerY);
            gc.fillText(TEXT_RESTART, MSG_RESTART_X, centerY + MSG_RESTART_OFFSET_Y);
        } else if (won) {
            gc.setFill(Color.YELLOW);
            gc.fillText(TEXT_WIN, MSG_WIN_X, centerY);
            gc.fillText(TEXT_RESTART, MSG_RESTART_X, centerY + MSG_RESTART_OFFSET_Y);
        }
    }
}