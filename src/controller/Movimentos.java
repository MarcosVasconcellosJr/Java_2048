package controller;

import Jogo2048.Jogo2048;
import model.Caixinha;

public class Movimentos extends Jogo2048 {

    public Movimentos() {
    }

    public boolean move(int contApartirdeTamXTam, int incX, int incY){
        
        boolean moved = false;
        
        for (int i = 0; i < super.tam * super.tam; i++) {
            
            int alcance;
            if((contApartirdeTamXTam - i) < 0){
                alcance = (contApartirdeTamXTam - i) * (-1);
            } else {
                alcance = contApartirdeTamXTam - i;
            }
            
            int linha = alcance / super.tam;
            int coluna = alcance % super.tam;

            //se não há instanciação de um objeto Caixinha dentro dessa possição [linha][coluna] da matriz então não 
            //precisamos mover nem mesclar esta posição com outra
            if(caixinha[linha][coluna] == null)
                continue;
            
            int proxLinha = linha + incY;
            int proxColuna = coluna + incX;

            //Verificar se a posição da próxima caixinha esta dentro da dimensão da matriz 0 -> 3
            while (verificaRangeTamMatriz(proxLinha, proxColuna)) {
                
                Caixinha proxima = super.caixinha[proxLinha][proxColuna];
                Caixinha atual = super.caixinha[linha][coluna];
                
                if (proxima == null) {//Verifica se a proxima posição esta vazia(em relação a atual)

                    if (super.temMovimentosPossiveis)//Se tem movimentos possiveis no container(tabuleiro), sai do WHILE
                        return true;

                    super.caixinha[proxLinha][proxColuna] = atual;//A proxima posição que esta vazia recebera o valor da atual da anterior a ela
                    
                    super.repaint();
                   

                    super.caixinha[linha][coluna] = null;//A posição anterior recebera NULL
                    System.out.println("Repaint");
                    super.repaint();
                   
                                        
                    //Andar com as posições
                    linha = proxLinha;
                    coluna = proxColuna;
                    proxLinha += incY;
                    proxColuna += incX;
                    moved = true;

                } else if (proxima.podeMesclarCom(atual)) {

                    if (super.temMovimentosPossiveis)
                        return true;

                    int value = proxima.mesclarCom(atual);
                    super.repaint();                  
                                        
                    //Verificação dos pontos
                    if (value > super.recorde)
                        super.recorde = value;
                        super.pontos += value;
                        super.caixinha[linha][coluna] = null;
                        moved = true;
                    break;
                } else
                    break;
            }
        }

        if (moved==true) {
            if (super.recorde < super.objetivo) {
                limparMescladas();//Limpa as caixinhas que ja foram mescladas com outras
                super.addCaixinhaRandomica();//coloca 2 ou 4 em posição aleatoria que esteja vazia
                if (!temMovimentos()) {//Se não tem mais possibilidades de movimento significa que PERDEU
                    super.statusDoJogo = "usuarioPerdeu";
                }
            } else if (super.recorde == super.objetivo)//Verifica se tem alguma caixinha com o valor 2048, caso tenha venceu o Jogo.
                super.statusDoJogo = "usuarioGanhou";
        }

        return moved;
    }

    //Função que verifica se cada uma das caixinhas é null se for seta o atributo mesclado delas como false
    public void limparMescladas() {
        for (Caixinha[] row : super.caixinha)
            for (Caixinha tile : row)
                if (tile != null)
                    tile.setMesclada(false);
    }

    public boolean temMovimentos() {
        super.temMovimentosPossiveis = true;
        boolean hasMoves = moveUp() || moveDown() || moveLeft() || moveRight();
        super.temMovimentosPossiveis = false;
        return hasMoves;
    }

    public boolean moveUp() {
        return move(0, 0, -1);
    }

    public boolean moveDown() {
        return move(super.tam * super.tam - 1, 0, 1);
    }

    public boolean moveLeft() {
        return move(0, -1, 0);
    }

    public boolean moveRight() {
        return move(super.tam * super.tam - 1, 1, 0);
    }

    public boolean verificaRangeTamMatriz(int pos1, int pos2){
        if(pos1 >= 0 && pos1 < super.tam){
            if(pos2 >= 0 && pos2 < super.tam){
                return true;
            }
        }
        return false;
    }
}