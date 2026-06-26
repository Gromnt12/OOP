package ru.nsu.bukhanov.snake.model;

import ru.nsu.bukhanov.snake.model.food.Apple;
import ru.nsu.bukhanov.snake.model.food.FastFood;
import ru.nsu.bukhanov.snake.model.food.SlowFood;
import ru.nsu.bukhanov.snake.model.food.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final double DEFAULT_SPEED = 1.0;
    private static final int PROBABILITY_MAX = 100;
    private static final int PROBABILITY_APPLE = 60;
    private static final int PROBABILITY_FAST = 80;

    private final int width;
    private final int height;
    private final int winLength;
    private final Snake snake;
    private final List<Food> foods = new ArrayList<>();
    private final List<Point> obstacles = new ArrayList<>();

    private final Random random = new Random();
    private boolean isGameOver = false;
    private boolean isGameWon = false;
    private double speedMultiplier = DEFAULT_SPEED;
    private boolean isStarted = false;

    public Game(int width, int height, int foodCount, int winLength) {
        this.width = width;
        this.height = height;
        this.winLength = winLength;

        this.snake = new Snake(new Point(width / 4, height / 2));

        int centerX = width / 2;
        int centerY = height / 2;
        obstacles.add(new Point(centerX, centerY));
        obstacles.add(new Point(centerX - 1, centerY));
        obstacles.add(new Point(centerX, centerY - 1));
        obstacles.add(new Point(centerX - 1, centerY - 1));

        for (int i = 0; i < foodCount; i++) spawnFood();
    }

    public boolean isStarted() { return isStarted; }
    public void start() { this.isStarted = true; }

    public void update() {
        if (!isStarted || isGameOver || isGameWon) return;
        if (snake.willCollide(width, height, obstacles)) {
            isGameOver = true;
            return;
        }
        snake.move();
        checkFoodCollision();

        if (snake.getBody().size() >= winLength) {
            isGameWon = true;
        }
    }

    private void checkFoodCollision() {
        Point head = snake.getBody().getFirst();
        Food eaten = null;

        for (Food f : foods) {
            if (f.getPosition().equals(head)) {
                f.applyEffect(snake, this);
                eaten = f;
                break;
            }
        }

        if (eaten != null) {
            foods.remove(eaten);
            spawnFood();
        }
    }

    private void spawnFood() {
        Point p;
        do {
            p = new Point(random.nextInt(width), random.nextInt(height));
        } while (snake.getBody().contains(p) || obstacles.contains(p));

        int foodChance = random.nextInt(PROBABILITY_MAX);
        if (foodChance < PROBABILITY_APPLE) {
            foods.add(new Apple(p));
        } else if (foodChance < PROBABILITY_FAST) {
            foods.add(new FastFood(p));
        } else {
            foods.add(new SlowFood(p));
        }
    }

    public double getSpeedMultiplier() { return speedMultiplier; }
    public void multiplySpeed(double factor) { this.speedMultiplier *= factor; }
    public Snake getSnake() { return snake; }
    public List<Food> getFoods() { return foods; }
    public List<Point> getObstacles() { return obstacles; }
    public boolean isGameOver() { return isGameOver; }
    public boolean isGameWon() { return isGameWon; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}