package ru.nsu.bukhanov.model;

public class Order {
    private final int id;
    private OrderState state;

    public Order(int id) {
        this.id = id;
        this.state = OrderState.PENDING;
        logState();
    }

    public void setState(OrderState state) {
        this.state = state;
        logState();
    }

    public int getId() {
        return id;
    }

    private void logState() {
        System.out.println("[" + id + "] [" + state + "]");
    }
}