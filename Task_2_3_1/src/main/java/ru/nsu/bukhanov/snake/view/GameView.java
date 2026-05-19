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
    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;

    private GameController controller;
    private static final int CELL_SIZE = 30;
    private static final long BASE_UPDATE_INTERVAL = 150_000_000;
    private long lastUpdate = 0;

    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        gc.setFont(new Font("Arial", 18));
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
            case W, UP -> controller.handleInput("UP");
            case S, DOWN -> controller.handleInput("DOWN");
            case A, LEFT -> controller.handleInput("LEFT");
            case D, RIGHT -> controller.handleInput("RIGHT");
            case R -> controller.handleInput("RESTART");
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
            case "FAST" -> gc.setFill(Color.YELLOW);
            case "SLOW" -> gc.setFill(Color.BLUE);
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
        gc.fillText("Длина: " + length, 20, 30);
        gc.fillText(String.format("Скорость: %.1fx", speed), 20, 55);

        if (!started) {
            gc.setFill(Color.WHITE);
            gc.fillText("НАЖМИТЕ WASD ИЛИ СТРЕЛКИ ДЛЯ СТАРТА", 80, height * CELL_SIZE / 2.0);
        } else if (gameOver) {
            gc.setFill(Color.WHITE);
            gc.fillText("ИГРА ОКОНЧЕНА!", 220, height * CELL_SIZE / 2.0);
            gc.fillText("Нажмите 'R' для рестарта", 195, height * CELL_SIZE / 2.0 + 30);
        } else if (won) {
            gc.setFill(Color.YELLOW);
            gc.fillText("ВЫ ПОБЕДИЛИ!", 230, height * CELL_SIZE / 2.0);
            gc.fillText("Нажмите 'R' для рестарта", 195, height * CELL_SIZE / 2.0 + 30);
        }
    }
}