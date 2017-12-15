/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.GUI;

import briscola.Client.Logic.Carta;
import briscola.Client.Logic.ClientProtocol;
import briscola.Client.Logic.ClientThread;
import briscola.Main;
import static briscola.Main.menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Gabriele
 */
public class New2PGame extends javax.swing.JPanel {

    
    //booleane per capire se bisogna disegnare qualcosa
    public boolean disegnaBriscola = true;
    public boolean disegnaMazzo = true;
    public boolean disegnaCartePreseG1 = false;
    public boolean disegnaCartePreseG2 = false;
    
    //booleana per capire quando il gioco deve finire
    public boolean finisci = false;
    
    //booleane per capire quale carta viene giocata
    public boolean cardG11played = false;
    public boolean cardG12played = false;
    public boolean cardG13played = false;
    public boolean cardG21played = false;
    public boolean cardG22played = false;
    public boolean cardG23played = false;
    
    //booleane per animare le prese di una mano
    private boolean prendiG1 = false;
    private boolean prendiG2 = false;
    public boolean prendiCartaG1 = false;
    public boolean prendiCartaG2 = false;
    
    //booleane per capire se bisogna pescare una carta
    public boolean pescaG11 = false;
    public boolean pescaG12 = false;
    public boolean pescaG13 = false;
    public boolean pescaG21 = false;
    public boolean pescaG22 = false;
    public boolean pescaG23 = false;

    //immagini del gioco
    public Image imageG11 = null;
    public Image imageG12 = null;
    public Image imageG13 = null;
    public Image imageG21;
    public Image imageG22;
    public Image imageG23;
    public Image briscola;
    public Image cardG1;
    public Image cardG2;
    public Image cardBack;
    public Image horizontalCardBack;
    private  BufferedImage [] sfondoTav;
    private  BufferedImage bgImage;

    //posizioni delle carte
    public int cartax;
    public int cartay;
    public int cartaPescataX;
    public int cartaPescataY;
    public int cartaGiocataG1x;
    public int cartaGiocataG1y;
    public int cartaGiocataG2x;
    public int cartaGiocataG2y;
    
    //carte del giocatore
    public Carta carta1;
    public Carta carta2;
    public Carta carta3;

    //oggetti necessari al gioco
    private New2PGame game;
    private ClientProtocol protocol;
    private ClientThread clientThread;
    public Thread animazioneIniziale;
    public JPanelVincita vincita;
    private Random random;
    
    //quale giocatore della stanza è
    public String player = "";
    
    //di chi è il turno
    public String turno = "";
    
    //immagine del tavolo
    private  int sceltaTav;
    
    //punteggi
    public int puntiG1 = 0;
    public int puntiG2 = 0;
    
    

    public New2PGame(ClientThread client) throws IOException {
        //inizializzo gli oggetti
        initComponents();
        game = this;
        clientThread = client;
        protocol = new ClientProtocol(client);
        this.setBackground(Color.black);
        random = new Random();
        
        //imposto le immagini di gioco
        int i = random.nextInt(2);
        if(i == 1){
            cardBack = paint("yugiohVerticale");
            imageG21 = paint("yugiohVerticale");
            imageG22 = paint("yugiohVerticale");
            imageG23 = paint("yugiohVerticale");
            horizontalCardBack = paint("yugiohOrizzontale");
        } else {
            cardBack = paint("pokemonVerticale");
            imageG21 = paint("pokemonVerticale");
            imageG22 = paint("pokemonVerticale");
            imageG23 = paint("pokemonVerticale");
            horizontalCardBack = paint("pokemonOrizzontale");
        }
        
        
        sceltaTav = random.nextInt(4);
        
        BufferedImage [] sfondoTav = new BufferedImage[4];
        
        try {
            sfondoTav[0] = paint("tav1");
            sfondoTav[1] = paint("tav2");
            sfondoTav[2] = paint("tav3");
            sfondoTav[3] = paint("tav4");
        } catch (IOException ex) {}
        
        bgImage=sfondoTav[sceltaTav];
        
        //imposto che in caso di uscita dal gioco debba mandare un messaggio
        //al server
        menu.setDefaultCloseOperation(0);
        menu.addWindowListener(new WindowAdapter() {
            @Override
             public void windowClosing(WindowEvent e){
                try{
                    //dentro al catch nel caso non abbia una connessione
                    //con un server
                    protocol.sendExitGame();
                } catch(Exception ex){}
                System.exit(1);
            }
        
        });
    }
    
    //metodo che inizializza il thread che da le carte
    public void generaThreadIniziale(){
        animazioneIniziale = new Thread(){
            @Override
            public void run(){
                //aspetto che il gioco sia visibile
                while(!game.isShowing()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {}
                }
                daiCarte();
            }
            
            private void daiCarte(){
                boolean g11 = false;
                boolean g12 = false;
                boolean g13 = false;
                boolean g21 = false;
                boolean g22 = false;
                boolean g23 = false;
                
                if(player.equals("g1")){
                    g11 = true;
                    //ripristino le coordinate della carta dal mazzo
                    game.getMazzoCoordinates();
                    //do la carta al G11
                    while(g11){
                        pescaG11 = true;
                        //parte l'animazione
                        repaint();
                        //quando ha finito di dare la carta al G11, la da al G21
                        if(!pescaG11){ g11 = false; g21 = true; }
                    }
                    //si ripete per ogni carta dei giocatori
                    game.getMazzoCoordinates();
                    while(g21){
                        pescaG21 = true;
                        repaint();
                        if(!pescaG21){ g21 = false; g12 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g12){
                        pescaG12 = true;
                        repaint();
                        if(!pescaG12){ g12 = false; g22 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g22){
                        pescaG22 = true;
                        repaint();
                        if(!pescaG22){ g22 = false; g13 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g13){
                        pescaG13 = true;
                        repaint();
                        if(!pescaG13){ g13 = false; g23 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g23){
                        pescaG23 = true;
                        repaint();
                        if(!pescaG23){ g23 = false; }
                    }
                } else {
                    g21 = true;
                    //ripristino le coordinate della carta dal mazzo
                    game.getMazzoCoordinates();
                    //do la carta al G12
                    while(g21){
                        pescaG21 = true;
                        //parte l'animazione
                        repaint();
                        //quando ha finito di dare la carta al G21, la da al G11
                        if(!pescaG21){ g21 = false; g11 = true; }
                    }
                    //si ripete per ogni carta dei giocatori
                    game.getMazzoCoordinates();
                    while(g11){
                        pescaG11 = true;
                        repaint();
                        if(!pescaG11){ g11 = false; g22 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g22){
                        pescaG22 = true;
                        repaint();
                        if(!pescaG22){ g22 = false; g12 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g12){
                        pescaG12 = true;
                        repaint();
                        if(!pescaG12){ g12 = false; g23 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g23){
                        pescaG23 = true;
                        repaint();
                        if(!pescaG23){ g23 = false; g13 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g13){
                        pescaG13 = true;
                        repaint();
                        if(!pescaG13){ g13 = false; }
                    }
                }
                try { 
                    join();
                } catch (InterruptedException ex) {}
            }
        };
    }

    //metodo repaint
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0,getWidth(), getHeight(), null);
        if(disegnaBriscola){
            g.drawImage(briscola, labelBriscola.getX(), labelBriscola.getY(), this);
        }
        if(disegnaMazzo){
            g.drawImage(horizontalCardBack, labelMazzo.getX(), labelMazzo.getY(), this);
        }
        if(disegnaCartePreseG1){
            if(player.equals("g1"))
                g.drawImage(cardBack, labelCartePreseG1.getX(), labelCartePreseG1.getY(), this);
            else
                g.drawImage(cardBack, labelCartePreseG2.getX(), labelCartePreseG2.getY(), this);
        }
        if(disegnaCartePreseG2){
            if(player.equals("g2"))
                g.drawImage(cardBack, labelCartePreseG1.getX(), labelCartePreseG1.getY(), this);
            else
                g.drawImage(cardBack, labelCartePreseG2.getX(), labelCartePreseG2.getY(), this);
        }
        if (cardG11played) {
            spostaCartaG11();
            g.drawImage(imageG11, cartax, cartay, this);
            repaint();
        }
        if (cardG12played) {
            spostaCartaG12();
            g.drawImage(imageG12, cartax, cartay, this);
            repaint();
        }
        if (cardG13played) {
            spostaCartaG13();
            g.drawImage(imageG13, cartax, cartay, this);
            repaint();
        }
        if (cardG21played) {
            spostaCartaG21();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG22played) {
            spostaCartaG22();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG23played) {
            spostaCartaG23();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if(prendiCartaG1){
            prendiCartaG1();
            g.drawImage(cardG1, cartaGiocataG1x, cartaGiocataG1y, this);
            repaint();
        }
        if(prendiCartaG2){
            prendiCartaG2();
            g.drawImage(cardG2, cartaGiocataG2x, cartaGiocataG2y, this);
            repaint();
        }
        if(prendiG1){
            prendiG1();
            g.drawImage(cardG1, cartaGiocataG1x, cartaGiocataG1y, this);
            repaint();
        }
        if(prendiG2){
            prendiG2();
            g.drawImage(cardG2, cartaGiocataG2x, cartaGiocataG2y, this);
            repaint();
        }
        if (pescaG11) {
            pescaCartaG11();
            g.drawImage(cardBack, cartaPescataX, cartaPescataY, this);
            repaint();
        }
        if (pescaG12) {
            pescaCartaG12();
            g.drawImage(cardBack, cartaPescataX, cartaPescataY, this);
            repaint();
        }
        if (pescaG13) {
            pescaCartaG13();
            g.drawImage(cardBack, cartaPescataX, cartaPescataY, this);
            repaint();
        }
        if (pescaG21) {
            pescaCartaG21();
            g.drawImage(cardBack, cartaPescataX, cartaPescataY, this);
            repaint();
        }
        if (pescaG22) {
            pescaCartaG22();
            g.drawImage(cardBack, cartaPescataX, cartaPescataY, this);
            repaint();
        }
        if (pescaG23) {
            pescaCartaG23();
            g.drawImage(cardBack, cartaPescataX, cartaPescataY, this);
            repaint();
        }
        if(finisci && tutteCarteGiocate()){
            restartGame();
        }
        g.setFont(new Font("Nirmala UI", 1, 24));
        g.setColor(Color.white);
        g.fillRect(labelCartePreseG1.getX() + 35, labelCartePreseG1.getY() + 75, 75, 25);
        g.fillRect(labelCartePreseG2.getX() + 35, labelCartePreseG2.getY() + 75, 75, 25);
        g.setColor(Color.black);
        g.drawRect(labelCartePreseG1.getX() + 35, labelCartePreseG1.getY() + 75, 75, 25);
        g.drawRect(labelCartePreseG2.getX() + 35, labelCartePreseG2.getY() + 75, 75, 25);
        String spazio1 = "";
        String spazio2 = "";
        if(puntiG1 < 10)
            spazio1 = "  ";
        else if(puntiG1 < 100)
            spazio1 = " ";
        else
            spazio1 = "";
        if(puntiG2 < 10)
            spazio2 = "  ";
        else if(puntiG2 < 100)
            spazio2 = " ";
        else spazio2 = "";
        g.drawString(spazio1 + puntiG1, labelCartePreseG1.getX() + 50, labelCartePreseG1.getY() + 95);
        g.drawString(spazio2 + puntiG2, labelCartePreseG2.getX() + 50, labelCartePreseG2.getY() + 95);
        repaint();
    }

    //metodo che data un'icona restitusce l'immagine dell'icona
    public Image getImage(Icon icon){
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        icon.paintIcon(null, g2, 0, 0);
        return image;
    }

    private void spostaCartaG11() {
        //sposto la carta fino a quando non si trova nella posizione giusta
        if (cartax > labelCartaGiocataG1.getX()) {
            cartax -= 10;
        }
        if (cartay > labelCartaGiocataG1.getY()) {
            cartay -= 10;
        }
        if (cartax <= labelCartaGiocataG1.getX() && cartay <= labelCartaGiocataG1.getY()) {
            //setto quindi l'icona dell'immagine della carta giocata nella label
            cardG11played = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
            ImageIcon img = new ImageIcon(imageG11);
            labelCartaGiocataG1.setIcon(img);
            cartax = labelCartaG21.getLocationOnScreen().x;
            cartay = labelCartaG21.getLocationOnScreen().y;
            //mando il messaggio al server dicendo chi ha mandato la carta,
            //quale carta ha mandato e in che posizione era nella mano
            protocol.playCard(player, cartaToString(carta1), 1);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG12() {
        if (cartax > labelCartaGiocataG1.getX()) {
            cartax -= 6;
        }
        if (cartay > labelCartaGiocataG1.getY()) {
            cartay -= 10;
        }
        if (cartax <= labelCartaGiocataG1.getX() && cartay <= labelCartaGiocataG1.getY()) {
            cardG12played = false;
            ImageIcon img = new ImageIcon(imageG12);
            labelCartaGiocataG1.setIcon(img);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
            cartax = labelCartaG22.getLocationOnScreen().x;
            cartay = labelCartaG22.getLocationOnScreen().y;
            protocol.playCard(player, cartaToString(carta2), 2);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG13() {
        if (cartax > labelCartaGiocataG1.getX()) {
            cartax -= 10;
        }
        if (cartay > labelCartaGiocataG1.getY()) {
            cartay -= 8;
        }
        if (cartax <= labelCartaGiocataG1.getX() && cartay <= labelCartaGiocataG1.getY()) {
            cardG13played = false;
            ImageIcon img = new ImageIcon(imageG13);
            labelCartaGiocataG1.setIcon(img);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
            cartax = labelCartaG23.getLocationOnScreen().x;
            cartay = labelCartaG23.getLocationOnScreen().y;
            protocol.playCard(player, cartaToString(carta3), 3);
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG21() {
        labelCartaG21.setIcon(null);
        if (cartax < labelCartaGiocataG2.getX()) {
            cartax += 10;
        }
        if (cartay < labelCartaGiocataG2.getY()) {
            cartay += 9;
        }
        if (cartax >= labelCartaGiocataG2.getX() && cartay >= labelCartaGiocataG2.getY()) {
            cardG21played = false;

            ImageIcon img = new ImageIcon(imageG21);
            labelCartaGiocataG2.setIcon(img);
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG22() {
        labelCartaG22.setIcon(null);
        if (cartax < labelCartaGiocataG2.getX()) {
            cartax += 10;
        }
        if (cartay < labelCartaGiocataG2.getY()) {
            cartay += 10;
        }
        if (cartax >= labelCartaGiocataG2.getX() && cartay >= labelCartaGiocataG2.getY()) {
            cardG22played = false;
            ImageIcon img = new ImageIcon(imageG22);
            labelCartaGiocataG2.setIcon(img);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG23() {
        labelCartaG23.setIcon(null);
        if (cartax < labelCartaGiocataG2.getX()) {
            cartax += 10;
        }
        if (cartay < labelCartaGiocataG2.getY()) {
            cartay += 8;
        }
        if (cartax >= labelCartaGiocataG2.getX() && cartay >= labelCartaGiocataG2.getY()) {
            cardG23played = false;
            ImageIcon img = new ImageIcon(imageG23);
            labelCartaGiocataG2.setIcon(img);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void pescaCartaG11() {
        if (cartaPescataX > labelCartaG11.getX()) {
            cartaPescataX -= 10;
        }
        if (cartaPescataY < labelCartaG11.getY()) {
            cartaPescataY += 3;
        }
        if (cartaPescataX <= labelCartaG11.getX() && cartaPescataY >= labelCartaG11.getY()) {
            ImageIcon img = new ImageIcon(imageG11);
            labelCartaG11.setIcon(img);
            pescaG11 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void pescaCartaG12() {
        if (cartaPescataX > labelCartaG12.getX()) {
            cartaPescataX -= 7;
        }
        if (cartaPescataY < labelCartaG12.getY()) {
            cartaPescataY += 3;
        }
        if (cartaPescataX <= labelCartaG12.getX() && cartaPescataY >= labelCartaG12.getY()) {
            ImageIcon img = new ImageIcon(imageG12);
            labelCartaG12.setIcon(img);
            pescaG12 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void pescaCartaG13() {
        if (cartaPescataX > labelCartaG13.getX()) {
            cartaPescataX -= 5;
        }
        if (cartaPescataY < labelCartaG13.getY()) {
            cartaPescataY += 3;
        }
        if (cartaPescataX <= labelCartaG13.getX() && cartaPescataY >= labelCartaG13.getY()) {
            ImageIcon img = new ImageIcon(imageG13);
            labelCartaG13.setIcon(img);
            pescaG13 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void pescaCartaG21() {
        if (cartaPescataX > labelCartaG21.getX()) {
            cartaPescataX -= 10;
        }
        if (cartaPescataY > labelCartaG21.getY()) {
            cartaPescataY -= 8;
        }
        if (cartaPescataX <= labelCartaG21.getX() && cartaPescataY <= labelCartaG21.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG21.setIcon(img);
            pescaG21 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void pescaCartaG22() {
        if (cartaPescataX > labelCartaG22.getX()) {
            cartaPescataX -= 5;
        }
        if (cartaPescataY > labelCartaG22.getY()) {
            cartaPescataY -= 5;
        }
        if (cartaPescataX <= labelCartaG22.getX() && cartaPescataY <= labelCartaG22.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG22.setIcon(img);
            pescaG22 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void pescaCartaG23() {
        if (cartaPescataX > labelCartaG23.getX()) {
            cartaPescataX -= 5;
        }
        if (cartaPescataY > labelCartaG23.getY()) {
            cartaPescataY -= 10;
        }
        if (cartaPescataX <= labelCartaG23.getX() && cartaPescataY <= labelCartaG23.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG23.setIcon(img);
            pescaG23 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void prendiCartaG1(){
        if(cartaGiocataG1x < labelCartaGiocataG2.getLocationOnScreen().x){
            cartaGiocataG1x += 5;
        }
        if(cartaGiocataG1x >= labelCartaGiocataG2.getLocationOnScreen().x){
            prendiCartaG1 = false;
            prendiG2 = true;
            repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
        }
    }
    
    private void prendiCartaG2(){
        if(cartaGiocataG2x > labelCartaGiocataG1.getLocationOnScreen().x){
            cartaGiocataG2x -= 5;
        }
        if(cartaGiocataG2x <= labelCartaGiocataG1.getLocationOnScreen().x){
            prendiCartaG2 = false;
            prendiG1 = true;
            repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
        }
    }
    
    private void prendiG1(){
        pulisciCarteGiocate();
        if(cartaGiocataG1x < labelCartePreseG1.getLocationOnScreen().x){
            cartaGiocataG1x += 6;
        }
        if(cartaGiocataG1y < labelCartePreseG1.getLocationOnScreen().y){
            cartaGiocataG1y += 4;
        }
        if(cartaGiocataG1x >= labelCartePreseG1.getLocationOnScreen().x && 
                cartaGiocataG1y >= labelCartePreseG1.getLocationOnScreen().y){
            prendiG1 = false;
            if(this.player.equals("g1"))
                disegnaCartePreseG1 = true;
            else
                disegnaCartePreseG2 = true;
        }
    }
    
    private void prendiG2(){
        pulisciCarteGiocate();
        if(cartaGiocataG2x < labelCartePreseG2.getLocationOnScreen().x){
            cartaGiocataG2x += 6;
        }
        if(cartaGiocataG2y > labelCartePreseG2.getLocationOnScreen().y){
            cartaGiocataG2y -= 4;
        }
        if(cartaGiocataG2x >= labelCartePreseG2.getLocationOnScreen().x && 
                cartaGiocataG2y <= labelCartePreseG2.getLocationOnScreen().y){
            prendiG2 = false;
            if(this.player.equals("g1"))
                disegnaCartePreseG2 = true;
            else
                disegnaCartePreseG1 = true;
        }
    }

    //metodo che elimina le immagini delle carte giocate
    private void pulisciCarteGiocate() {
        labelCartaGiocataG1.setIcon(null);
        labelCartaGiocataG2.setIcon(null);
    }
    
    //metodo che ottiene le coordinate del mazzo
    public void getMazzoCoordinates(){
        //ripristina le coordinate
        cartaPescataX = labelMazzo.getLocationOnScreen().x;
        cartaPescataY = labelMazzo.getLocationOnScreen().y;
    }
    
    //metodo che fa ripartire il gioco quando è finito
    private void restartGame() {
        finisci = false;
        //mostro il nuovo pannello di vincita
        vincita = new JPanelVincita();
        protocol.restartGame(2);
        if(puntiG1 > puntiG2){
            vincita.labelVittoria.setText("Hai vinto!");
            vincita.labelVittoria.setBackground(Color.green);
            
        }
        else if(puntiG2 > puntiG1){
            vincita.labelVittoria.setBackground(Color.red);
            vincita.labelVittoria.setText("Hai perso!");
        }
        else{
            vincita.labelVittoria.setText("C'è un pareggio!");
        }
        vincita.validate();
        vincita.repaint();
        vincita.labelPuntiG1.setText("Punti G1: " + puntiG1);
        vincita.labelPuntiG2.setText("Punti G2: " + puntiG2);
        vincita.panelVincita.setLocation(300, 250);
        vincita.panelVincita.setVisible(true);
        vincita.panelVincita.setEnabled(true);
        Main.menu.getContentPane().removeAll();
        Main.menu.add(Main.new2PGame.vincita);
        Main.menu.pack();
        Main.new2PGame.validate();
        Main.new2PGame.repaint();
        Main.menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Thread wait = new Thread(){
            @Override
            public void run(){
                //aspetto che passino 10 secondi
                int aspetta = 10;
                while(aspetta > 0){
                    if(aspetta == 1)
                        vincita.labelSecondi.setText("La partita ricomincierà tra " + aspetta + " secondo");
                    else
                        vincita.labelSecondi.setText("La partita ricomincierà tra " + aspetta + " secondi");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {};
                    aspetta--;
                }
                try {
                    //creo una nuova partita
                    New2PGame new2PGame = new New2PGame(clientThread);
                    Main.menu.getContentPane().removeAll();
                    Main.menu.add(new2PGame);
                    Main.menu.pack();
                    new2PGame.validate();
                    new2PGame.repaint();
                    Main.menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } catch (IOException ex) {}
            }
        };
        wait.start();
    }
    
    //metodo che data una carta ritorna la sua stringa da utilizzare nel protocollo
    private String cartaToString(Carta c){
        //calcolo la stringa della carta
        int numero = c.getNumero();
        String n = "";
        if(numero < 10){
            n = "0" + numero + c.getSmallSeme();
        } else {
            n = numero + c.getSmallSeme();
        }
        return n;
    }
    
    //motodo che dice se tutte le carte dei giocatori sono state giocate
    private boolean tutteCarteGiocate(){
        if(labelCartaG11.getIcon() == null && 
                 labelCartaG12.getIcon() == null &&
                 labelCartaG13.getIcon() == null &&
                 labelCartaG21.getIcon() == null &&
                 labelCartaG22.getIcon() == null &&
                 labelCartaG23.getIcon() == null &&
                 labelCartaGiocataG1.getIcon() == null && 
                 labelCartaGiocataG2.getIcon() == null)
            return true;
        else
             return false;
     }
    
    //metodo per disegnare più facilmente le carte
    private BufferedImage paint(String carta) throws IOException {
        return ImageIO.read(this.getClass().getResource("../Immagini/" + carta + ".png"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelCartaG11 = new javax.swing.JLabel();
        labelCartaG12 = new javax.swing.JLabel();
        labelCartaG13 = new javax.swing.JLabel();
        labelCartaG21 = new javax.swing.JLabel();
        labelCartaG22 = new javax.swing.JLabel();
        labelCartaG23 = new javax.swing.JLabel();
        labelMazzo = new javax.swing.JLabel();
        labelBriscola = new javax.swing.JLabel();
        labelCartaGiocataG2 = new javax.swing.JLabel();
        labelCartaGiocataG1 = new javax.swing.JLabel();
        labelCartePreseG2 = new javax.swing.JLabel();
        labelCartePreseG1 = new javax.swing.JLabel();

        setLayout(null);

        labelCartaG11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG11MouseClicked(evt);
            }
        });
        add(labelCartaG11);
        labelCartaG11.setBounds(44, 543, 140, 204);

        labelCartaG12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG12MouseClicked(evt);
            }
        });
        add(labelCartaG12);
        labelCartaG12.setBounds(190, 543, 140, 204);

        labelCartaG13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG13MouseClicked(evt);
            }
        });
        add(labelCartaG13);
        labelCartaG13.setBounds(336, 543, 140, 204);
        add(labelCartaG21);
        labelCartaG21.setBounds(44, 6, 140, 204);
        add(labelCartaG22);
        labelCartaG22.setBounds(190, 6, 140, 204);
        add(labelCartaG23);
        labelCartaG23.setBounds(336, 6, 140, 204);
        add(labelMazzo);
        labelMazzo.setBounds(616, 394, 204, 140);

        labelBriscola.setToolTipText("");
        add(labelBriscola);
        labelBriscola.setBounds(643, 286, 140, 102);
        add(labelCartaGiocataG2);
        labelCartaGiocataG2.setBounds(336, 295, 140, 204);
        add(labelCartaGiocataG1);
        labelCartaGiocataG1.setBounds(44, 295, 140, 204);
        add(labelCartePreseG2);
        labelCartePreseG2.setBounds(532, 6, 140, 204);
        add(labelCartePreseG1);
        labelCartePreseG1.setBounds(532, 546, 140, 204);
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void labelCartaG11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG11MouseClicked
        // TODO add your handling code here:
        //se è il mio turno
        if(turno.equals(player)){
            //se non l'ho già giocata
            if(labelCartaG11.getIcon() != null){
                //metto = true la booleana corrispondente e faccio un repaint
                imageG11 = getImage(labelCartaG11.getIcon());
                cartax = labelCartaG11.getLocationOnScreen().x;
                cartay = labelCartaG11.getLocationOnScreen().y;
                cardG11played = true;
                labelCartaG11.setIcon(null);
                repaint();
            }
        }
    }//GEN-LAST:event_labelCartaG11MouseClicked

    private void labelCartaG12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG12MouseClicked
        // TODO add your handling code here:
        if(turno.equals(player)){
            if(labelCartaG12.getIcon() != null){
                imageG12 = getImage(labelCartaG12.getIcon());
                cartax = labelCartaG12.getLocationOnScreen().x;
                cartay = labelCartaG12.getLocationOnScreen().y;
                cardG12played = true;
                labelCartaG12.setIcon(null);
                repaint();
            }
        }
    }//GEN-LAST:event_labelCartaG12MouseClicked

    private void labelCartaG13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG13MouseClicked
        // TODO add your handling code here:
        if(turno.equals(player)){
            if(labelCartaG13.getIcon() != null){
                imageG13 = getImage(labelCartaG13.getIcon());
                cartax = labelCartaG13.getLocationOnScreen().x;
                cartay = labelCartaG13.getLocationOnScreen().y;
                cardG13played = true;
                labelCartaG13.setIcon(null);
                repaint();
            }
        }
    }//GEN-LAST:event_labelCartaG13MouseClicked
    
     
     

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel labelBriscola;
    public javax.swing.JLabel labelCartaG11;
    public javax.swing.JLabel labelCartaG12;
    public javax.swing.JLabel labelCartaG13;
    public javax.swing.JLabel labelCartaG21;
    public javax.swing.JLabel labelCartaG22;
    public javax.swing.JLabel labelCartaG23;
    public javax.swing.JLabel labelCartaGiocataG1;
    public javax.swing.JLabel labelCartaGiocataG2;
    private javax.swing.JLabel labelCartePreseG1;
    private javax.swing.JLabel labelCartePreseG2;
    public javax.swing.JLabel labelMazzo;
    // End of variables declaration//GEN-END:variables

    

    
}
