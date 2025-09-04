package com.example.heapsort;

import com.example.heapsort.Controller.TimSort;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setScene(scene);
//        stage.show();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/tim.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 200);
        stage.setTitle("TimSort Interativo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        TimSort ts = new TimSort();

        // Preenche o vetor com valores aleatórios de 0 a 99
        Random rand = new Random();
        for (int i = 0; i < ts.vetOriginal.length; i++) {
            ts.vetOriginal[i] = rand.nextInt(100);
        }

        // Exibe vetor antes da ordenação
        System.out.print("Vetor antes: ");
        for (int v : ts.vetOriginal) {
            System.out.print(v + " ");
        }
        System.out.println();

        // Executa TimSort
        ts.TimSort();

        // Exibe vetor depois da ordenação
        System.out.print("Vetor depois: ");
        for (int v : ts.vetOriginal) {
            System.out.print(v + " ");
        }
        System.out.println();
    }


    }


