package ru.nsu.bukhanov.game;

import ru.nsu.bukhanov.io.IO;
import ru.nsu.bukhanov.model.Card;
import ru.nsu.bukhanov.model.Deck;
import ru.nsu.bukhanov.model.Hand;
import ru.nsu.bukhanov.participants.Dealer;

import java.util.Random;

public class Game {
    private final IO io;
    private final int decksCount;
    private final Random random;
    private final Deck fixedDeckOrNull;

    private int playerWins = 0;
    private int dealerWins = 0;
    private int round = 0;

    public Game(IO io, int decksCount, Random random) {
        this(io, decksCount, random, null);
    }

    public Game(IO io, int decksCount, Random random, Deck fixedDeck) {
        this.io = io;
        this.decksCount = decksCount <= 0 ? 1 : decksCount;
        this.random = random;
        this.fixedDeckOrNull = fixedDeck;
    }

    public void start() {
        io.println("Добро пожаловать в Блэкджек!");
        Deck deck = (fixedDeckOrNull != null) ? fixedDeckOrNull : new Deck(decksCount, random);

        boolean continuePlaying = true;
        while (continuePlaying) {
            round++;
            io.println("");
            io.println("Раунд " + round);

            if (fixedDeckOrNull == null && deck.remaining() < 15) {
                deck = new Deck(decksCount, random);
                io.println("Перетасовываем колоду...");
            }

            playSingleRound(deck);
            io.println(String.format("Счет %d:%d в вашу пользу.", playerWins, dealerWins));
            io.print("Сыграть ещё раунд? (1 = да, 0 = нет): ");
            String ans = io.readLine();
            continuePlaying = "1".equals(ans.trim());
        }
        io.println("Спасибо за игру!");
    }

    private void playSingleRound(Deck deck) {
        Hand player = new Hand();
        Dealer dealer = new Dealer();

        // Раздача
        Card p1 = deck.draw(); player.add(p1);
        Card d1 = deck.draw(); dealer.hand().add(d1);
        Card p2 = deck.draw(); player.add(p2);
        Card d2 = deck.draw(); dealer.hand().add(d2);

        io.println("Дилер раздал карты");
        io.println("Ваши карты: " + player.toRuList(false) + " > " + player.bestValue());
        io.println("Карты дилера: [" + d1.toRu() + ", <закрытая карта ]");

        // Блэкджек после раздачи
        boolean playerBJ = player.isBlackjack();
        boolean dealerBJ = dealer.blackjack();
        if (playerBJ || dealerBJ) {
            io.println("Открываем карты для проверки блэкджека...");
            io.println("Карты дилера: " + dealer.hand().toRuList(false) + " > " + dealer.score());
            if (playerBJ && dealerBJ) {
                io.println("Ничья (оба блекджек).");
            } else if (playerBJ) {
                io.println("У вас Блэкджек! Вы выиграли раунд!");
                playerWins++;
            } else {
                io.println("У дилера Блэкджек. Вы проиграли раунд.");
                dealerWins++;
            }
            return;
        }

        // Ход игрока
        io.println("");
        io.println("Ваш ход");
        io.println("-------");
        boolean playerTurn = true;
        while (playerTurn) {
            io.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .");
            String cmd = io.readLine().trim();
            if ("1".equals(cmd)) {
                Card c = deck.draw();
                player.add(c);
                io.println("Вы открыли карту " + c.toRu());
                io.println("Ваши карты: " + player.toRuList(false) + " > " + player.bestValue());
                io.println("Карты дилера: [" + d1.toRu() + ", <закрытая карта ]");

                if (player.isBust()) {
                    io.println("Перебор! Вы проиграли раунд.");
                    dealerWins++;
                    return;
                }
            } else if ("0".equals(cmd)) {
                playerTurn = false;
            } else {
                io.println("Неизвестная команда. Повторите.");
            }
        }

        // Ход дилера
        io.println("Ход дилера");
        io.println("-------");
        io.println("Дилер открывает закрытую карту " + d2.toRu());
        io.println("Ваши карты: " + player.toRuList(false) + " > " + player.bestValue());
        io.println("Карты дилера: " + dealer.hand().toRuList(false) + " > " + dealer.score());

        dealer.play(deck, io);
        io.println("Ваши карты: " + player.toRuList(false) + " > " + player.bestValue());
        io.println("Карты дилера: " + dealer.hand().toRuList(false) + " > " + dealer.score());

        if (dealer.bust()) {
            io.println("Дилер перебрал. Вы выиграли раунд!");
            playerWins++;
            return;
        }

        int ps = player.bestValue();
        int ds = dealer.score();
        if (ps > ds) {
            io.println("Вы выиграли раунд!");
            playerWins++;
        } else if (ps < ds) {
            io.println("Вы проиграли раунд.");
            dealerWins++;
        } else {
            io.println("Ничья.");
        }
    }
}
