package Jogo2048;

import model.Caixinha;
import controller.Movimentos;
import arquivo.Arquivos;

//TO-DO
//Fazer desfazer etapa
//Fazer refazer etapa

/*
* Criado Por: 
* Marcos Junior - 18720920
* Hiago Silva - 18726455
*
* Este programa é uma cópia barata do jogo 2048, cuja finalidade
* é juntar pares de valores iguais a fim de somar um quadrado de 
* valor = 2048
 */

 /*Importações das libs gráficas e de eventos para manusear todas as alterações
* e para controlar a interação entre o usuário e a aplicação.*/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import static sun.security.krb5.Confounder.bytes;

// Classe principal que declara as variáveis do 
// programa e chama outras classes e métodos
public class Jogo2048 extends JPanel {
    String arq = "src\\assets\\maximumhighScore.txt";
    final Color[] tabelaDeCores = {new Color(0x701710), new Color(0xFFE4C3), new Color(0xfff4d3), new Color(0xffdac3),
        new Color(0xE7B08E), new Color(0xE7BF8E), new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
        new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)}; // Cores usadas

    public static int objetivo = 2048; // Objetivo padrão do jogo, alcançar recorde = 2048
    public static int recorde;
    public static int pontos; // Pontuação atual do jogador

    public Color caixaCor = new Color(0x4EB7CC); // Cor do container de fundo do jogo 2048
    public Color corCaixinhaVazia = new Color(0xEBEBEB);// Coir da caixinha do número
    public Color textos = new Color(0x506266);
    public Color corTelaDeInicio = new Color(0xEBEBEB); // Cor de fundo da tela inicial
    public Color cabecalho = new Color(0xe8649b);
    public Color blocosCabecalho = new Color(0xEBEBEB);

    public static Caixinha[][] caixinha; // Declaração da matriz que será usada para toda a lógica do joguinho
    public int tam = 4; // usada para declarar as dimensões da matriz

    public static String statusDoJogo = "inicio"; // usada para tomar ações dependendo do status atual que o usuário está
    public boolean temMovimentosPossiveis; // Usada para fazer a verificação dos movimentos para os 4 lados.
    public Movimentos mover;
    
    public static String conteudo = "";
    
    public int clicouEmX;
    public int clicouEmY;
    public int soltouEmX;
    public int soltouEmY;
    public int dX = 0;
    public int dY = 0;

    public Jogo2048() {
        setPreferredSize(new Dimension(900, 700));// tamanho da tela a ser exibida
        setBackground(new Color(0xEBEBEB));// seta uma cor para o fundo do JPanel
        setFont(new Font("SansSerif", Font.BOLD, 48));// escolha da fonte para o jogo e tambem o tamanho dos numeros a
        // serem exibidos.
        
        setFocusable(true);// Fazer com que os Jpanel seja ouvido pelos eventos

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                clicouEmX = e.getX();
                clicouEmY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseEvents(e);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jogo2048.class.getName()).log(Level.SEVERE, null, ex);
                }
                repaint(); // Ele habilita um update no redesenhamento do jpanel quando os métodos gráficos são chamados
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        mover.moveUp();
                        break;
                    case KeyEvent.VK_W:
                        mover.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        mover.moveDown();
                        break;
                    case KeyEvent.VK_S:
                        mover.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        mover.moveLeft();
                        break;
                    case KeyEvent.VK_A:
                        mover.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        mover.moveRight();
                        break;
                    case KeyEvent.VK_D:
                        mover.moveRight();
                        break;
                    case KeyEvent.VK_ENTER:
                        iniciarJogo();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        statusDoJogo = "inicio";
                        break;
                    case KeyEvent.VK_X:
                        caixinha[2][3].Caixinha(1024);
                        caixinha[2][1].Caixinha(1024);
                        repaint();
                        break;
                }
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try {
            drawGrid(g);
        } catch (IOException ex) {
            Logger.getLogger(Jogo2048.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarJogo() {
        if (statusDoJogo != "usuarioJogando") {
            pontos = 0;
            recorde = 0;
            statusDoJogo = "usuarioJogando";
            caixinha = new Caixinha[tam][tam];
            mover = new Movimentos();
            addCaixinhaRandomica();
            addCaixinhaRandomica();
        }
    }

    public void mouseEvents(MouseEvent e) {
        try {
            dX = 0;
            dY = 0;
            soltouEmX = e.getX();
            soltouEmY = e.getY();
            dX = (soltouEmX - clicouEmX);
            dY = (soltouEmY - clicouEmY);

            //System.out.println("click x: " + clicouEmX + " clickY: " + clicouEmY + "\nexitX: " + soltouEmX + " exitY: " + soltouEmY);

            if (dX < 0 && dY < 0) {
                dX = (dX * (-1));
                dY = (dY * (-1));
                if (dX > dY) {
                    System.out.println("Esquerda");
                    mover.moveLeft();
                    dX = (dX * (-1));
                    dY = (dY * (-1));
                } else {
                    System.out.println("Cima");
                    mover.moveUp();
                    dX = (dX * (-1));
                    dY = (dY * (-1));
                }
            }

            if (dX > 0 && dY < 0) {
                dY = (dY * (-1));

                if (dX > dY) {
                    System.out.println("Direita");
                    mover.moveRight();
                    dY = (dY * (-1));

                } else {
                    System.out.println("Cima");
                    mover.moveUp();
                    dY = (dY * (-1));
                }
            }

            if (dX > 0 && dY > 0) {
                if (dX > dY) {
                    mover.moveRight();
                } else {
                    mover.moveDown();
                }
            }

            if (dX < 0 && dY > 0) {
                dX = (dX * (-1));

                if (dX > dY) {
                    System.out.println("Esquerda");
                    mover.moveLeft();
                    dX = (dX * (-1));

                } else {
                    System.out.println("Baixo");
                    mover.moveDown();
                    dX = (dX * (-1));
                }
            }
        } catch (java.lang.NullPointerException e1) {
        }
    }

    public void drawGrid(Graphics2D g) throws IOException {
        // Desenha o container e o cabecalho
        g.setColor(caixaCor);
        g.fillRoundRect(200, 100, 500, 500, 25, 25);
        g.setColor(cabecalho);
        g.fillRoundRect(200, 20, 500, 70, 25, 25);

        // Blocos de desfazer
        g.setColor(blocosCabecalho);
        g.fillRoundRect(215, 30, 60, 50, 25, 25);
        g.setColor(blocosCabecalho);
        g.fillRoundRect(290, 30, 60, 50, 25, 25);

        // Blocos de Score atual e HighScore
        // Desenha o score do usuário
        g.setColor(blocosCabecalho);
        g.fillRoundRect(365, 30, 150, 50, 25, 25);
        g.setColor(textos);
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        String s = String.valueOf(pontos);
        g.drawString("Score: " + s, 375, 63);
        // Desenha o highScore
        g.setColor(blocosCabecalho);
        g.fillRoundRect(530, 30, 150, 50, 25, 25);
        g.setColor(textos);
        // g.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        s = Arquivos.Read(arq);
        conteudo = Arquivos.Read(arq);
        //System.out.println(conteudo);
        double c = Double.parseDouble(conteudo);

        
        if(pontos >= c){
            Arquivos.Write(arq, Integer.toString(pontos));
            String l = Integer.toString(pontos);
            g.drawString("HighScore: " + l, 540, 63);
        }else{
        
            g.drawString("HighScore: " + s, 540, 63);

        }
        
      

        if (statusDoJogo == "usuarioJogando") {
            for (int linha = 0; linha < tam; linha++) {
                for (int coluna = 0; coluna < tam; coluna++) {
                    if (caixinha[linha][coluna] == null) {
                        g.setColor(corCaixinhaVazia);
                        g.fillRoundRect(215 + coluna * 121, 115 + linha * 121, 106, 106, 25, 25);
                    } else {
                        g.setFont(new Font("SansSerif", Font.BOLD, 40));
                        drawTile(g, linha, coluna);
                    }
                }
            }
        } else {
            g.setColor(corTelaDeInicio);
            g.fillRoundRect(215, 115, 469, 469, 25, 25);

            g.setColor(textos);
            g.setFont(new Font("SansSerif", Font.BOLD, 20));

            switch (statusDoJogo) {
                case "usuarioGanhou":
                    g.drawString("Você Ganhou", 390, 350);
                    break;
                case "usuarioPerdeu":
                    g.drawString("Você Perdeu", 390, 350);
                    break;
                case "inicio":
                    g.setFont(new Font("SansSerif", Font.BOLD, 150));
                    g.drawString("2048", 289, 395);
                    g.setColor(caixaCor);

                    g.setFont(new Font("SansSerif", Font.BOLD, 20));
                    g.drawString("Pressione enter para continuar", 300, 470);
                    g.drawString("Você pode usar as setas para jogar", 280, 530);
                    break;
                    
            }
        }
    }

    public void drawTile(Graphics2D g, int linha, int coluna) throws IOException {
        int value = caixinha[linha][coluna].pegaValor();
        g.setColor(tabelaDeCores[(int) (Math.log(value) / Math.log(2)) + 1]);
        g.fillRoundRect(215 + coluna * 121, 115 + linha * 121, 106, 106, 25, 25);
        String s = String.valueOf(value);

        ///Aqui vaem os if e else

        g.setColor(value < 128 ? tabelaDeCores[0] : tabelaDeCores[1]);

        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int dec = fm.getDescent();

        //int x = 215 + coluna * 121 + (106 - fm.stringWidth(s)) / 2;
        //int y = 115 + linha * 121 + (asc + (106 - (asc + dec)) / 2);
        int x = 175 + coluna * 121 + (106 - fm.stringWidth(s)) / 2;
        int y = 50 + linha * 121 + (asc + (106 - (asc + dec)) / 2);
        Image icon = new ImageIcon(getClass().getResource("/assets/giphy.gif")).getImage();

        if(value==2){
        BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/2.png"));
        g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==4){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/4.png"));
            g.drawImage(icon, x, y, 100, 100, this);
        }else if(value==8){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/8.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==16){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/16.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==32){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/32.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==64){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/64.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==128){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/128.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==256){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/256.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==512){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/512.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==1024){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/1024.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }else if(value==2048){
            BufferedImage imagem_2 = ImageIO.read(getClass().getResourceAsStream("/assets/2048.png"));
            g.drawImage(imagem_2, x, y, 100, 100, null);
        }
    }

    // Função faz o trabalho exclusivo de adicionar uma caixinha
    // através de um numero aleatório para colocar um novo objeto de=o tipo caixinha
    // em uma posição da matriz de objetos chamada caixinha
    public void addCaixinhaRandomica() {

        Random rand = new Random();// Cria a váriavel Randômica
        int flag = 0;// Setamos como 1 a flag e assim o do while para seu processo
        int maxInteracoes = 0;// Controlamos o (do while) para que ele não entre em um loop infinito

        do {
            int n = rand.nextInt(4);// seta um número aleatorio entre 0 e 3 para o N(nsera a posição na matriz)
            int n1 = rand.nextInt(4);// seta outro número para a posição na matriz
            int valorRand = rand.nextInt(2);// Gera 1 ou 0 para escolher se o número que aparecera sera 2 ou 4

            if (caixinha[n][n1] == null) {
                if (valorRand == 0) {
                    caixinha[n][n1] = new Caixinha(2);
                } else if (valorRand == 1) {
                    caixinha[n][n1] = new Caixinha(4);
                }
                flag = 1;
            } else {
                maxInteracoes += 1;
            }
        } while (flag == 0 && maxInteracoes <= 16);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("2048 by Hiago and Marcos");
            f.setResizable(true);
            f.add(new Jogo2048(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
