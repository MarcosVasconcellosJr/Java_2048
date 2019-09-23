
package jogo_2048;

import java.util.Scanner;

public class Jogo_2048 {

    public static void main(String[] args) {
        
        Matriz matriz = new Matriz();
       
       Scanner ler = new Scanner(System.in);
       String letra;
       matriz.colocaValor(0, 0, 2);
        matriz.colocaValor(0, 1, 2);
         matriz.colocaValor(0, 2, 2);
          matriz.colocaValor(0, 3, 2);
        
        matriz.imprime();
        matriz.setas("d");
          matriz.imprime();    
    }
    
}
