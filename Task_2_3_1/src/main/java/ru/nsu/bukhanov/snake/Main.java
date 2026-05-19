package ru.nsu.bukhanov.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.bukhanov.snake.view.GameView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game_scene.fxml"));
        BorderPane root = loader.load();
        GameView view = loader.getController();

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> view.handleInput(event.getCode()));

        primaryStage.setTitle("Snake MVC OOP");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}