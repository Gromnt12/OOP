package ru.nsu.bukhanov.snake.model;
import java.util.LinkedList;

public class Snake {
    private final LinkedList<Point> body = new LinkedList<>();
    private Direction currentDirection = Direction.RIGHT;
    private int growthPending = 0;

    public Snake(Point start) {
        body.add(start);
    }

    public LinkedList<Point> getBody() { return body; }
    public Direction getDirection() { return currentDirection; }

    public void setDirection(Direction newDir) {
        if (currentDirection == Direction.UP && newDir == Direction.DOWN) return;
        if (currentDirection == Direction.DOWN && newDir == Direction.UP) return;
        if (currentDirection == Direction.LEFT && newDir == Direction.RIGHT) return;
        if (currentDirection == Direction.RIGHT && newDir == Direction.LEFT) return;
        this.currentDirection = newDir;
    }

    public void grow(int amount) {
        growthPending += amount;
    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = null;

        switch (currentDirection) {
            case UP -> newHead = new Point(head.x, head.y - 1);
            case DOWN -> newHead = new Point(head.x, head.y + 1);
            case LEFT -> newHead = new Point(head.x - 1, head.y);
            case RIGHT -> newHead = new Point(head.x + 1, head.y);
        }

        body.addFirst(newHead);

        if (growthPending > 0) {
            growthPending--;
        } else {
            body.removeLast();
        }
    }

    public boolean willCollide(int width, int height, java.util.List<Point> obstacles) {
        Point head = body.getFirst();
        Point next = null;

        switch (currentDirection) {
            case UP -> next = new Point(head.x, head.y - 1);
            case DOWN -> next = new Point(head.x, head.y + 1);
            case LEFT -> next = new Point(head.x - 1, head.y);
            case RIGHT -> next = new Point(head.x + 1, head.y);
        }

        if (next.x < 0 || next.x >= width || next.y < 0 || next.y >= height) return true;

        if (obstacles.contains(next)) return true;

        int limit = growthPending > 0 ? body.size() : body.size() - 1;
        for (int i = 0; i < limit; i++) {
            if (next.equals(body.get(i))) return true;
        }

        return false;
    }
}