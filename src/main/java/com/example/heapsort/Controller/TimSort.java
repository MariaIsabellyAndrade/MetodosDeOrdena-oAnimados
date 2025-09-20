package com.example.heapsort.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Random;
import java.util.Arrays;

public class TimSort {

    @FXML
    private Button bt0, bt1, bt2, bt3, bt4, bt5, btIniciar,bt6,bt7;

    private Button[] vet; // vetor de botões
    private int[] vetOriginal; // array com os valores numéricos
    private final int minRun = 2; // minRun adaptado para um array pequeno (6 elementos)
    private static final String[] CORES_RUNS = {
            "#add8e6", "#f08080", "#90ee90", "#ffdab9", "#dda0dd", "#e0ffff", "#f4a460"
    };
    private String[] coresAtuaisDosBotoes;
    private int indiceCorAtual = 0;

    @FXML
    public void initialize() {
        vet = new Button[]{bt0, bt1, bt2, bt3, bt4, bt5,bt6,bt7};
        vetOriginal = new int[vet.length];
        coresAtuaisDosBotoes = new String[vet.length];
        inicializarVetor();
    }

    // --- Métodos de Inicialização e Animação ---
    private void inicializarVetor() {
        Random random = new Random();
        for (int i = 0; i < vet.length; i++) {
            vetOriginal[i] = random.nextInt(100);
            final int finalI = i; // Cria uma cópia final da variável de iteração
            Platform.runLater(() -> {
                vet[finalI].setText(String.valueOf(vetOriginal[finalI]));
                vet[finalI].setStyle("-fx-background-color: lightgray;");
            });
            coresAtuaisDosBotoes[i] = "-fx-background-color: lightgray;";
        }
    }

    @FXML
    private void acaoIniciar() {
        btIniciar.setDisable(true);
        indiceCorAtual = 0;
        new Thread(this::TimSortAnimado).start();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void atualizaTextoBotao(int index, String texto) {
        Platform.runLater(() -> vet[index].setText(texto));
    }

    private void setEstiloBotao(int index, String style) {
        Platform.runLater(() -> vet[index].setStyle(style));
    }

    private void colorirRun(int inicio, int fim) {
        String cor = CORES_RUNS[indiceCorAtual % CORES_RUNS.length];
        String estilo = "-fx-background-color: " + cor + ";";
        for (int i = inicio; i <= fim; i++) {
            coresAtuaisDosBotoes[i] = estilo;
            final int finalI = i; // Garante que a variável é final para uso na lambda
            Platform.runLater(() -> setEstiloBotao(finalI, estilo));
        }
        indiceCorAtual++;
    }

    // --- Lógica de Ordenação com Animação ---

    private void InsertSort(int inicio, int fim) {
        for (int i = inicio + 1; i <= fim; i++) {
            final int finalI = i;
            int key = vetOriginal[i];
            int j = i - 1;

            // Destaque do elemento a ser inserido
            setEstiloBotao(finalI, "-fx-background-color: orange;");
            sleep(400);

            while (j >= inicio && vetOriginal[j] > key) {
                final int finalJ = j;
                setEstiloBotao(finalJ, "-fx-background-color: lightcoral;"); // Destaque do elemento sendo movido
                sleep(400);

                vetOriginal[j + 1] = vetOriginal[j];
                atualizaTextoBotao(j + 1, String.valueOf(vetOriginal[j]));

                // Restaura a cor anterior
                setEstiloBotao(finalJ, coresAtuaisDosBotoes[finalJ]);
                j--;
            }
            vetOriginal[j + 1] = key;
            final int finalJ = j;
            atualizaTextoBotao(finalJ + 1, String.valueOf(key));

            // Restaura as cores originais após a inserção
            setEstiloBotao(finalI, coresAtuaisDosBotoes[finalI]);
            setEstiloBotao(finalJ + 1, coresAtuaisDosBotoes[finalJ+1]);
        }
    }
    // Código corrigido no método merge
    private void merge(int l, int m, int r) {
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];

        for (int i = 0; i < len1; i++) left[i] = vetOriginal[l + i];
        for (int j = 0; j < len2; j++) right[j] = vetOriginal[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < len1 && j < len2) {
            final int finalI = i;
            final int finalJ = j;
            setEstiloBotao(l + finalI, "-fx-background-color: yellow; -fx-border-color: black;");
            setEstiloBotao(m + 1 + finalJ, "-fx-background-color: yellow; -fx-border-color: black;");
            sleep(400);

            if (left[finalI] <= right[finalJ]) {
                vetOriginal[k] = left[finalI];
                i++;
            } else {
                vetOriginal[k] = right[finalJ];
                j++;
            }

            // --- CORREÇÃO: VERIFICAÇÃO DE ÍNDICES VÁLIDOS ---
            if (i > 0) {
                final int prevI = i - 1;
                setEstiloBotao(l + prevI, coresAtuaisDosBotoes[l + prevI]);
            }
            if (j > 0) {
                final int prevJ = j - 1;
                setEstiloBotao(m + 1 + prevJ, coresAtuaisDosBotoes[m + 1 + prevJ]);
            }
            // --------------------------------------------------

            final int finalK = k;
            atualizaTextoBotao(finalK, String.valueOf(vetOriginal[finalK]));
            setEstiloBotao(finalK, "-fx-background-color: pink;");
            sleep(400);
            setEstiloBotao(finalK, coresAtuaisDosBotoes[finalK]);
            k++;
        }
        // O restante do código do merge, para os elementos restantes
        while (i < len1) {
            final int finalI = i;
            final int finalK = k;
            vetOriginal[finalK] = left[finalI];
            atualizaTextoBotao(finalK, String.valueOf(left[finalI]));
            setEstiloBotao(finalK, "-fx-background-color: pink;");
            sleep(400);
            setEstiloBotao(finalK, coresAtuaisDosBotoes[finalK]);
            i++;
            k++;
        }
        while (j < len2) {
            final int finalJ = j;
            final int finalK = k;
            vetOriginal[finalK] = right[finalJ];
            atualizaTextoBotao(finalK, String.valueOf(right[finalJ]));
            setEstiloBotao(finalK, "-fx-background-color: pink;");
            sleep(400);
            setEstiloBotao(finalK, coresAtuaisDosBotoes[finalK]);
            j++;
            k++;
        }
    }
    private void TimSortAnimado() {
        int n = vetOriginal.length;

        // --- Fase 1: Encontrar e ordenar as runs ---
        for (int i = 0; i < n; i += minRun) {
            final int finalI = i;
            int fim = Math.min(finalI + minRun - 1, n - 1);
            InsertSort(finalI, fim);
            colorirRun(finalI, fim);
            sleep(1000); // Pausa para visualizar cada run
        }

        // --- Fase 2: Juntar (merge) as runs ---
        for (int tamanhoRun = minRun; tamanhoRun < n; tamanhoRun = 2 * tamanhoRun) {
            final int finalTamanhoRun = tamanhoRun;
            for (int inicio = 0; inicio < n; inicio += 2 * finalTamanhoRun) {
                final int finalInicio = inicio;
                int meio = finalInicio + finalTamanhoRun - 1;
                int fim = Math.min(finalInicio + 2 * finalTamanhoRun - 1, n - 1);

                if (meio < fim) {
                    merge(finalInicio, meio, fim);
                    colorirRun(finalInicio, fim);
                    sleep(1000); // Pausa para visualizar cada merge
                }
            }
        }

        Platform.runLater(() -> {
            btIniciar.setDisable(false);
            btIniciar.setText("Reiniciar");
        });
    }
}