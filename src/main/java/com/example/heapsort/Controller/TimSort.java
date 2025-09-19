package com.example.heapsort.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Random;
import java.util.Stack;

public class TimSort {

    @FXML
    private Button bt0, bt1, bt2, bt3, bt4, bt5, btIniciar;

    private Button[] vet;       // vetor de botões
    public int[] vetOriginal = new int[6];
    private int minRun = 32;

    @FXML
    public void initialize() {
        vet = new Button[]{bt0, bt1, bt2, bt3, bt4, bt5};
        Random random = new Random();

        double larguraPane = 600; // largura do Pane igual ao HeapSort
        double spacing = 80;       // mesmo espaçamento
        double offsetX = (larguraPane - (vet.length - 1) * spacing) / 2; // centraliza

        for (int i = 0; i < vet.length; i++) {
            vetOriginal[i] = random.nextInt(100);
            vet[i].setText(String.valueOf(vetOriginal[i]));
            vet[i].setLayoutX(offsetX + i * spacing); // X centralizado
            vet[i].setLayoutY(100);                   // Y igual ao HeapSort
        }
    }

    @FXML
    private void acaoIniciar() {
        new Thread(() -> TimSortAnimado()).start();
    }
    private void trocarBotoesAnimados(int x, int j) {
        double startX0 = vet[x].getLayoutX();
        double startX1 = vet[j].getLayoutX();
        double distancia = startX1 - startX0; // pode ser negativo
        int passos = (int) (Math.abs(distancia) / 6);

        // Determina a direção horizontal
        double dx = distancia / passos; // passo horizontal
        double dy = 6;                  // passo vertical

        // Sobe/Desce os botões
        for (int i = 0; i < passos; i++) {
            Platform.runLater(() -> {
                vet[x].setLayoutY(vet[x].getLayoutY() + dy);
                vet[j].setLayoutY(vet[j].getLayoutY() - dy);
            });
            sleep(50);
        }

        // Move na horizontal
        for (int i = 0; i < passos; i++) {
            Platform.runLater(() -> {
                vet[x].setLayoutX(vet[x].getLayoutX() + dx);
                vet[j].setLayoutX(vet[j].getLayoutX() - dx);
            });
            sleep(50);
        }

        // Volta para altura original
        for (int i = 0; i < passos; i++) {
            Platform.runLater(() -> {
                vet[x].setLayoutY(vet[x].getLayoutY() - dy);
                vet[j].setLayoutY(vet[j].getLayoutY() + dy);
            });
            sleep(50);
        }

        // Troca os botões no array
        Button auxB = vet[x];
        vet[x] = vet[j];
        vet[j] = auxB;

        // Troca os valores correspondentes
        int auxV = vetOriginal[x];
        vetOriginal[x] = vetOriginal[j];
        vetOriginal[j] = auxV;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void InsertSort(int[] vet, int inicio, int fim, int auxRun) {
        for (int i = inicio + 1; i <= fim; i++) {
            int key = vet[i];
            int j = i - 1;
            while (j >= inicio && vet[j] > key) {
                vet[j + 1] = vet[j];
                trocarBotoesAnimados(j + 1, j); // animação da troca
                j--;
            }
            vet[j + 1] = key;
        }
    }

    private void mergeSort(int[] vet, int start1, int end1, int start2, int end2) {
        int n1 = end1 - start1 + 1;
        int n2 = end2 - start2 + 1;
        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; i++) left[i] = vet[start1 + i];
        for (int j = 0; j < n2; j++) right[j] = vet[start2 + j];

        int i = 0, j = 0, k = start1;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                vet[k] = left[i++];
            } else {
                vet[k] = right[j++];
            }
            trocarBotoesAnimados(k, k); // atualiza posição visual
            k++;
        }

        while (i < n1) {
            vet[k] = left[i++];
            trocarBotoesAnimados(k, k);
            k++;
        }
        while (j < n2) {
            vet[k] = right[j++];
            trocarBotoesAnimados(k, k);
            k++;
        }
    }

    private void TimSortAnimado() {
        int inicio, fim = 0;
        int i = 0;
        Stack<int[]> pilha = new Stack<>();

        while (i < vetOriginal.length) {
            inicio = i;
            fim = i;

            while (i + 1 < vetOriginal.length && vetOriginal[i] <= vetOriginal[i + 1]) {
                fim = i + 1;
                i++;
            }

            if (fim > inicio) {
                int aux = (fim - inicio) + 1;
                if (aux < minRun) {
                    int newEnd = inicio + minRun - 1;
                    if (newEnd >= vetOriginal.length) newEnd = vetOriginal.length - 1;
                    InsertSort(vetOriginal, inicio, newEnd, newEnd - inicio + 1);
                    fim = newEnd;
                }
                pilha.push(new int[]{inicio, fim});
            }

            while (i + 1 < vetOriginal.length && vetOriginal[i] > vetOriginal[i + 1]) {
                fim = i + 1;
                i++;
            }

            if (fim > inicio) {
                int aux = (fim - inicio) + 1;
                if (aux < minRun) {
                    int newEnd = inicio + minRun - 1;
                    if (newEnd >= vetOriginal.length) newEnd = vetOriginal.length - 1;
                    InsertSort(vetOriginal, inicio, newEnd, newEnd - inicio + 1);
                    fim = newEnd;
                }
                pilha.push(new int[]{inicio, fim});
            }

            i = fim + 1;
        }

        i = 0;
        while (pilha.size() > 1) {
            int[] run1 = pilha.get(i);
            int[] run2 = pilha.get(i + 1);
            mergeSort(vetOriginal, run1[0], run1[1], run2[0], run2[1]);
            pilha.remove(i + 1);
            pilha.remove(i);
            pilha.add(i, new int[]{run1[0], run2[1]});
            i = 0;
        }
    }
}
