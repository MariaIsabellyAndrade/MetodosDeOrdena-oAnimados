package com.example.heapsort;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o FXML do Menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml")); // nome do seu FXML
        Parent root = loader.load();

        // Configura a cena e a janela principal
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Menu de Ordenação");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
