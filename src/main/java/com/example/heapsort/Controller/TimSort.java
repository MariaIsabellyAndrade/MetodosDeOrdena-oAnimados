package com.example.heapsort.Controller;
import javafx.scene.control.Button;

import java.util.Random;
import java.util.Stack;

public class TimSort {

    public int[] vetOriginal = new int[6];


    private int calculaRun(int variavel) {
        int r = 0;
        while (variavel >= 32) {
            r |= (variavel & 1);
            variavel >>= 1;
        }
        return variavel + r;
    }
    private int  minRun = calculaRun(vetOriginal.length);

    private int[] inverte(int inicio, int fim, int[] vetor) {
        int i = inicio, j=fim, aux;
        while(i < j) {
            aux = vetor[i];
            vetor[i]= vetor[j];
            vetor[j] = aux;
            i++;
            j--;
        }
        return vetor;
    }

    private void InsertSort(int[] vetOriginal, int inicio, int fim, int auxRun) {
        for (int i = inicio + 1; i <= fim; i++) {
            int key = vetOriginal[i];
            int j = i - 1;
            while (j >= inicio && vetOriginal[j] > key)
            {
                vetOriginal[j + 1] = vetOriginal[j];
                j--;
            }
            vetOriginal[j + 1] = key;
        }
    }

    private void merge(int start1, int end1, int start2, int end2) {
        int nL = end1 - start1 + 1;
        int nR = end2 - start2 + 1;

        int[] L = new int[nL];
        int[] R = new int[nR];

        for (int i = 0; i < nL; i++) L[i] = vetOriginal[start1 + i];
        for (int j = 0; j < nR; j++) R[j] = vetOriginal[start2 + j];

        int i = 0, j = 0, k = start1;

        while (i < nL && j < nR) {
            if (L[i] <= R[j]) {
                vetOriginal[k++] = L[i++];
            } else {
                vetOriginal[k++] = R[j++];
            }
        }

        while (i < nL) vetOriginal[k++] = L[i++];
        while (j < nR) vetOriginal[k++] = R[j++];
    }


    private void MergeSort(Stack<int[]> pilha) {
        int i = 0;
        while (pilha.size() > 1) {

            while (i + 1 < pilha.size()) {
                int[] run1 = pilha.get(i);
                int[] run2 = pilha.get(i + 1);

                int start1 = run1[0];
                int end1 = run1[1];
                int start2 = run2[0];
                int end2 = run2[1];

                if (end1 + 1 == start2) { // runs contíguas
                    merge(start1, end1, start2, end2);
                    pilha.remove(i + 1);
                    pilha.remove(i);
                    pilha.add(i, new int[]{start1, end2});
                    i = 0; // reinicia para tentar novas fusões
                } else {
                    i++;
                }
            }
            // Se não houver fusão possível, o loop externo vai terminar
        }
    }



    public void TimSort() {
        int inicio, fim = 0;
        boolean descrecente = false, crescente = false;

        int i = 0;
        Stack<int[]> pilha = new Stack<>();

        while (i < vetOriginal.length) {
            inicio = i;
            fim = i;
            crescente = false;
            descrecente = false;

            // Detecta run crescente
            while (i + 1 < vetOriginal.length && vetOriginal[i] < vetOriginal[i + 1]) {
                crescente = true;
                i++;
                fim = i;
            }

            // Processa run crescente
            if (fim > inicio && crescente) {
                int aux = (fim - inicio) + 1;

                // Se a run for menor que minRun, expandir até minRun ou fim do vetor
                if (aux < minRun) {
                    int newEnd = inicio + minRun - 1;
                    if (newEnd >= vetOriginal.length) {
                        newEnd = vetOriginal.length - 1;
                    }
                    InsertSort(vetOriginal, inicio, newEnd, newEnd - inicio + 1);
                    fim = newEnd; // atualiza fim antes de empilhar
                }

                pilha.push(new int[]{inicio, fim});
            }

            // Detecta run decrescente
            while (i + 1 < vetOriginal.length && vetOriginal[i] > vetOriginal[i + 1]) {
                descrecente = true;
                i++;
                fim = i;
            }

            // Processa run decrescente
            if (fim > inicio && descrecente) {
                int aux = (fim - inicio) + 1;

                if (aux < minRun) {
                    inverte(inicio, fim, vetOriginal);

                    int newEnd = inicio + minRun - 1;
                    if (newEnd >= vetOriginal.length) {
                        newEnd = vetOriginal.length - 1;
                    }

                    InsertSort(vetOriginal, inicio, newEnd, newEnd - inicio + 1);
                    fim = newEnd; // atualiza fim antes de empilhar
                } else {
                    inverte(inicio, fim, vetOriginal); // só inverte, sem ordenar
                }

                pilha.push(new int[]{inicio, fim});
            }

            i = fim + 1;
        }

        // Faz a fusão de todas as runs
        MergeSort(pilha);
    }


}