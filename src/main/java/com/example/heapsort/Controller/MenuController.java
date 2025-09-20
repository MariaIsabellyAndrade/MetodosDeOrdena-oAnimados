package com.example.heapsort.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    @FXML
    private Button btHeap;

    @FXML
    private Button btTim;

    @FXML
    private void abrirHeapSort() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/heapsort/hello-view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Animação de Ordenação - Heap Sort");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void abrirTimSort() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/heapsort/tim.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Animação de Ordenação - Tim Sort");
        stage.setScene(new Scene(root));
        stage.show();
    }
}