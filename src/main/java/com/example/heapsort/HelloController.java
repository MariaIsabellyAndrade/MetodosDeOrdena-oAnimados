package com.example.heapsort;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
        Platform.runLater(() -> {
            vet[x].setStyle("-fx-background-color: orange;");
            vet[j].setStyle("-fx-background-color: orange;");
        });

        Line seta = new Line();
        Platform.runLater(() -> {
            seta.setStroke(Color.RED);
            seta.setStrokeWidth(2);
            paneArea.getChildren().add(seta);
        });

        double startX0 = vet[x].getLayoutX();
        double startX1 = vet[j].getLayoutX();
        int passos = 30; // mais passos = mais devagar
        double deltaX = (startX1 - startX0) / passos;

        for (int i = 0; i < passos; i++) {
            Platform.runLater(() -> {
                vet[x].setLayoutX(vet[x].getLayoutX() + deltaX);
                vet[j].setLayoutX(vet[j].getLayoutX() - deltaX);

                // atualiza posição da seta durante o movimento
                seta.setStartX(vet[x].getLayoutX() + vet[x].getWidth()/2);
                seta.setStartY(vet[x].getLayoutY() + vet[x].getHeight());
                seta.setEndX(vet[j].getLayoutX() + vet[j].getWidth()/2);
                seta.setEndY(vet[j].getLayoutY());
            });

            try {
                Thread.sleep(100); // mais tempo = mais devagar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // troca lógica no array
        Button aux = vet[x];
        vet[x] = vet[j];
        vet[j] = aux;

        Platform.runLater(() -> {
            vet[x].setStyle("");
            vet[j].setStyle("");
            paneArea.getChildren().remove(seta);
        });
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
