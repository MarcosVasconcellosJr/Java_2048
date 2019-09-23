package jogo_2048;

import java.util.Random;

public class Matriz {

    public int[][] matriz = new int[4][4];
    Random r = new Random();
    int n = r.nextInt(4);
    int n1 = r.nextInt(4);
    //matriz[0][0]=3;

    public Matriz() {
    }

    public void colocaValor(int a, int b, int c) {
        matriz[a][b] = c;
    }

    public void imprime() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // matriz[i][j] = 1;
                System.out.print(" " + matriz[i][j]);
            }
            System.out.println();
        }
    }

    public void colocaNumRandom() {

        int flag = 0;
        do {
            int n = r.nextInt(4);
            int n1 = r.nextInt(4);
            if (matriz[n][n1] == 0) {
                matriz[n][n1] = 2;
                flag = 1;
            }
        } while (flag == 0);
    }

    public void setas(String a) {
        if (a == "d") {
            int z = 0;
            int y = 3;
            if (matriz[z][y - 1] == matriz[z][y]) {
                matriz[z][y] = matriz[z][y] + matriz[z][y - 1];
                matriz[z][y - 1] = 0;

                if (matriz[z][z] == matriz[z][z + 1]) {
                    matriz[z][z + 1] = matriz[z][z + 1] + matriz[z][z];
                    matriz[z][z] = 0;
                    matriz[z][z + 2] = matriz[z][z + 1];
                    matriz[z][z + 1] = 0;
                } else {
                    matriz[z][z + 2] = matriz[z][z + 1];
                    matriz[z][z + 1] = matriz[z][z];
                    matriz[z][z] = 0;
                }

            } else if (matriz[z][y - 1] == matriz[z][y - 2]) {
                matriz[z][y - 1] = matriz[z][y - 1] + matriz[z][y - 2];
                matriz[z][y - 2] = matriz[z][y - 3];
                matriz[z][y - 3] = 0;
                if (matriz[z][y] == 0) {
                    matriz[z][y] = matriz[z][y - 1];
                    matriz[z][y - 1] = matriz[z][y - 2];
                    matriz[z][y - 2] = matriz[z][y - 3];
                    matriz[z][y - 3] = 0;
                }

            } else {

            }
        }
    }
}
