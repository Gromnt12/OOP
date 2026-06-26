package ru.nsu.bukhanov.core;

import ru.nsu.bukhanov.config.ConfigReader;
import ru.nsu.bukhanov.config.PizzeriaConfig;
import ru.nsu.bukhanov.model.Order;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        PizzeriaConfig config = ConfigReader.readConfig("src/main/resources/config.json");
        Pizzeria pizzeria = new Pizzeria(config);

        pizzeria.start();

        // Симуляция поступления заказов
        for (int i = 1; i <= 15; i++) {
            pizzeria.addOrder(new Order(i));
            Thread.sleep(300);
        }

        System.out.println("\n--- Пиццерия закрывается, новые заказы не принимаются ---\n");
        pizzeria.stopGracefully();

        // Пытаемся добавить заказы после закрытия пиццерии
        System.out.println("Попытка добавить 'Опоздавший заказ 16'...");
        pizzeria.addOrder(new Order(16));

        System.out.println("Попытка добавить 'Опоздавший заказ 17'...");
        pizzeria.addOrder(new Order(17));

        // Дадим потокам курьеров (если они еще не прерваны жестко) секунду чтобы завершить вывод в консоль
        Thread.sleep(1000);
        System.out.println("\n--- Работа приложения завершена ---");
    }
}