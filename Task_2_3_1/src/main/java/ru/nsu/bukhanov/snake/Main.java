package ru.nsu.bukhanov.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.nsu.bukhanov.snake.view.GameView;

public class Main extends Application {
    private static final String FXML_PATH = "/fxml/game_scene.fxml";
    private static final String WINDOW_TITLE = "Snake MVC OOP";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH));
        BorderPane root = loader.load();
        GameView view = loader.getController();

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> view.handleInput(event.getCode()));

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}