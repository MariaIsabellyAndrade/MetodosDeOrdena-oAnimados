package com.example.heapsort;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.Random;

public class HelloController {
    private int[] valores = new int[6];
    private Button[] vet;

    @FXML
    private Button bt0, bt1, bt2, bt3, bt4, bt5;

    @FXML
    private Pane paneArea;

    @FXML
    private void acaoIniciar() {
        new Thread(this::HeapSort).start();
    }

    @FXML
    public void initialize() {
        vet = new Button[]{bt0, bt1, bt2, bt3, bt4, bt5};
        Random random = new Random();

        double larguraPane = 600; // largura do Pane
        double spacing = 80;
        double offsetX = (larguraPane - (vet.length - 1) * spacing) / 2; // centraliza os botões

        for (int i = 0; i < vet.length; i++) {
            valores[i] = random.nextInt(100);
            vet[i].setText(String.valueOf(valores[i]));

            // posição inicial dentro do pane, centralizada
            vet[i].setLayoutX(offsetX + i * spacing);
            vet[i].setLayoutY(100); // altura fixa
        }
    }

    public void trocarBotoesAnimados(int x, int j) {
        double startX0 = vet[x].getLayoutX();
        double startX1 = vet[j].getLayoutX();
        double distancia = startX1 - startX0; // distância relativa

        int passos = 10; // mais suave
        double deltaX = distancia / passos;

        for (int i = 0; i < passos; i++) {
            Platform.runLater(() -> {
                vet[x].setLayoutX(vet[x].getLayoutX() + deltaX);
                vet[j].setLayoutX(vet[j].getLayoutX() - deltaX);
            });
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // troca lógica no array
        Button aux = vet[x];
        vet[x] = vet[j];
        vet[j] = aux;
    }

    public void HeapSort() {
        int TL2 = valores.length;

        while (TL2 > 1) {
            int pai = (TL2 / 2) - 1;
            while (pai >= 0) {
                int FE = 2 * pai + 1;
                int FD = FE + 1;
                int maior = pai;

                if (FE < TL2 && valores[FE] > valores[maior]) maior = FE;
                if (FD < TL2 && valores[FD] > valores[maior]) maior = FD;

                if (maior != pai) {
                    int temp = valores[pai];
                    valores[pai] = valores[maior];
                    valores[maior] = temp;
                    trocarBotoesAnimados(pai, maior);

                    try { Thread.sleep(600); } catch (InterruptedException e) { e.printStackTrace(); }
                }

                pai--;
            }

            int temp = valores[0];
            valores[0] = valores[TL2 - 1];
            valores[TL2 - 1] = temp;
            trocarBotoesAnimados(0, TL2 - 1);

            try { Thread.sleep(600); } catch (InterruptedException e) { e.printStackTrace(); }

            TL2--;
        }
    }
}
