package com.example.heapsort;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class HelloController {

    @FXML
    private Button bt0, bt1, bt2, bt3, bt4, bt5;


    @FXML
    private Pane paneArea;

    // Array para armazenar os valores dos botões
    private int[] valores = new int[6];
    private Button[] vet;

    @FXML
    private void acaoIniciar() {
        new Thread(() -> HeapSort()).start();
    }

    @FXML
    public void initialize() {
        vet = new Button[]{bt0, bt1, bt2, bt3, bt4, bt5};
        Random random = new Random();

        for (int i = 0; i < vet.length; i++) {
            valores[i] = random.nextInt(100);
            vet[i].setText(String.valueOf(valores[i]));

            // define posição inicial dentro do pane
            vet[i].setLayoutX(i * 80); // espaçamento horizontal
            vet[i].setLayoutY(50);     // altura fixa

        }
    }


    public void trocarBotoesAnimados(int x, int j) {

        double startX0 = vet[x].getLayoutX();
        double startX1 = vet[j].getLayoutX();
        double distancia = Math.abs(startX1 - startX0); // distância entre eles

        int passos = (int) (distancia / 6);

        for (int i = 0; i < passos; i++) {
            // Muda a posição vertical do primeiro botão (vet[0]) para baixo
            Platform.runLater(() -> vet[x].setLayoutY(vet[x].getLayoutY() + 6));
            // Muda a posição vertical do segundo botão (vet[1]) para cima
            Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() - 6));

            // Pausa o loop por 50 milissegundos para criar o efeito de animação
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < passos; i++) {


            Platform.runLater(() -> vet[x].setLayoutX(vet[x].getLayoutX() + 6));
            Platform.runLater(() -> vet[j].setLayoutX(vet[j].getLayoutX() - 6));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < passos; i++) {


            Platform.runLater(() -> vet[x].setLayoutY(vet[x].getLayoutY() - 6));
            Platform.runLater(() -> vet[j].setLayoutY(vet[j].getLayoutY() + 6));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Button aux = vet[x];
        vet[x] = vet[j];
        vet[j] = aux;
    }


    public void HeapSort() {

        int pai, FE, FD, maior, TL2, aux;
        TL2 = valores.length;

        while (TL2 > 1) {
            pai = (TL2 / 2) - 1;
            maior = pai;
            while (pai >= 0) {
                FE = 2 * pai + 1;
                FD = FE + 1;
                if (FD < TL2) { // existe filho esquerdo
                    if (FE < TL2) { // existe filho direito também
                        if (valores[FD] < valores[FE]) {
                            maior = FE;
                        } else {
                            maior = FD;
                        }
                    } else {
                        maior = FD; // só existe o esquerdo
                    }
                } else
                    maior = FE;
                if (valores[pai] < valores[maior]) {

                    // troca também os valores
                    int temp = valores[maior];
                    valores[maior] = valores[pai];
                    valores[pai] = temp;
                    trocarBotoesAnimados(pai, maior);
                    // animação da troca
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                pai--;
            }
            int temp = valores[0];
            valores[0] = valores[TL2 - 1];
            valores[TL2 - 1] = temp;
            trocarBotoesAnimados(0, TL2 - 1);

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TL2--;
        }
    }
}

