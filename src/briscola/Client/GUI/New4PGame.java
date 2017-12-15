/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.GUI;

import briscola.Client.Logic.Carta;
import briscola.Client.Logic.ClientProtocol;
import briscola.Client.Logic.ClientThread;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Gabriele
 */
public class New4PGame extends javax.swing.JPanel {

    /**
     * Creates new form New4PGame
     */
    public boolean disegnaBriscola = true;
    public boolean disegnaMazzo = true;
    public boolean disegnaCartePreseG1 = false;
    public boolean disegnaCartePreseG2 = false;
    public boolean finisci = false;
    
    public boolean cardG11played = false;
    public boolean cardG12played = false;
    public boolean cardG13played = false;
    public boolean cardG21played = false;
    public boolean cardG22played = false;
    public boolean cardG23played = false;
    public boolean cardG31played = false;
    public boolean cardG32played = false;
    public boolean cardG33played = false;
    public boolean cardG41played = false;
    public boolean cardG42played = false;
    public boolean cardG43played = false;
    
    public boolean prendiG1 = false;
    public boolean prendiG2 = false;
    public boolean prendiG3 = false;
    public boolean prendiG4 = false;
    public boolean prendiCartaG1 = false;
    public boolean prendiCartaG2 = false;
    
    private boolean pescaG11 = false;
    private boolean pescaG12 = false;
    private boolean pescaG13 = false;
    private boolean pescaG21 = false;
    private boolean pescaG22 = false;
    private boolean pescaG23 = false;
    private boolean pescaG31 = false;
    private boolean pescaG32 = false;
    private boolean pescaG33 = false;
    private boolean pescaG41 = false;
    private boolean pescaG42 = false;
    private boolean pescaG43 = false;

    public Image imageG11 = null;
    public Image imageG12 = null;
    public Image imageG13 = null;
    public Image imageG21;
    public Image imageG22;
    public Image imageG23;
    public Image imageG31;
    public Image imageG32;
    public Image imageG33;
    public Image imageG41;
    public Image imageG42;
    public Image imageG43;
    public Image cardG1;
    public Image cardG2;
    public Image cardG3;
    public Image cardG4;
    public Image briscola;
    public Image cardBack;
    public Image horizontalCardBack;
    
    public int cartax;
    public int cartay;
    private int cartaPescataX;
    private int cartaPescataY;
    public int cartaGiocataG1x;
    public int cartaGiocataG1y;
    public int cartaGiocataG2x;
    public int cartaGiocataG2y;
    public int cartaGiocataG3x;
    public int cartaGiocataG3y;
    public int cartaGiocataG4x;
    public int cartaGiocataG4y;
    
    public int puntiS1;
    public int puntiS2;
    
    private New4PGame game;
    
    public Carta carta1;
    public Carta carta2;
    public Carta carta3;
    
    private  BufferedImage [] sfondoTav;
    private  BufferedImage bgImage;
    private  int sceltaTav;
    private  Random random;
    
    public String player = "";
    public String turno = "";
    
    private ClientThread client;
    private ClientProtocol protocol;
    public Thread animazioneIniziale;

    public New4PGame(ClientThread client) throws IOException {
        initComponents();
        game = this;
        this.client = client;
        this.setBackground(Color.black);
        
        protocol = new ClientProtocol(client);
        
        random = new Random();
        int i = random.nextInt(2);
        
        if(i == 1){
            imageG21 = paint("yugiohVerticale");
            imageG22 = paint("yugiohVerticale");
            imageG23 = paint("yugiohVerticale");
            imageG31 = paint("yugiohVerticale");
            imageG32 = paint("yugiohVerticale");
            imageG33 = paint("yugiohVerticale");
            imageG41 = paint("yugiohVerticale");
            imageG42 = paint("yugiohVerticale");
            imageG43 = paint("yugiohVerticale");
            cardBack = paint("yugiohVerticale");
            horizontalCardBack = paint("yugiohOrizzontale");
        } else {
            cardBack = paint("pokemonVerticale");
            imageG21 = paint("pokemonVerticale");
            imageG22 = paint("pokemonVerticale");
            imageG23 = paint("pokemonVerticale");
            imageG31 = paint("pokemonVerticale");
            imageG32 = paint("pokemonVerticale");
            imageG33 = paint("pokemonVerticale");
            imageG41 = paint("pokemonVerticale");
            imageG42 = paint("pokemonVerticale");
            imageG43 = paint("pokemonVerticale");
            horizontalCardBack = paint("pokemonOrizzontale");
        }

        
        sceltaTav = random.nextInt(4);
        
        BufferedImage [] sfondoTav = new BufferedImage[4];
        
        try {
            sfondoTav[0] = paint("tav1");
            sfondoTav[1] = paint("tav2");
            sfondoTav[2] = paint("tav3");
            sfondoTav[3] = paint("tav4");
        } catch (IOException ex) {
            Logger.getLogger(JPanelLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        bgImage = sfondoTav[sceltaTav];
        
    }

    @Override
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
            if(player.equals("g1") || player.equals("g3"))
                g.drawImage(horizontalCardBack, labelCartePreseS1.getX(), labelCartePreseS1.getY(), this);
            else
                g.drawImage(horizontalCardBack, labelCartePreseS2.getX(), labelCartePreseS2.getY(), this);
        }
        if(disegnaCartePreseG2){
            if(player.equals("g2") || player.equals("g4"))
                g.drawImage(horizontalCardBack, labelCartePreseS1.getX(), labelCartePreseS1.getY(), this);
            else
                g.drawImage(horizontalCardBack, labelCartePreseS2.getX(), labelCartePreseS2.getY(), this);
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
        if (cardG31played) {
            spostaCartaG31();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG32played) {
            spostaCartaG32();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG33played) {
            spostaCartaG33();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG41played) {
            spostaCartaG41();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG42played) {
            spostaCartaG42();
            g.drawImage(cardBack, cartax, cartay, this);
            repaint();
        }
        if (cardG43played) {
            spostaCartaG43();
            g.drawImage(cardBack, cartax, cartay, this);
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
        if(prendiG3){
            prendiG3();
            g.drawImage(cardG3, cartaGiocataG3x, cartaGiocataG3y, this);
            repaint();
        }
        if(prendiG4){
            prendiG4();
            g.drawImage(cardG4, cartaGiocataG4x, cartaGiocataG4y, this);
            repaint();
        }
        g.setFont(new Font("Nirmala UI", 1, 24));
        g.setColor(Color.white);
        g.fillRect(labelCartePreseS1.getX() + 35, labelCartePreseS1.getY() + 75, 75, 25);
        g.fillRect(labelCartePreseS2.getX() + 35, labelCartePreseS2.getY() + 75, 75, 25);
        g.setColor(Color.black);
        g.drawRect(labelCartePreseS1.getX() + 35, labelCartePreseS1.getY() + 75, 75, 25);
        g.drawRect(labelCartePreseS2.getX() + 35, labelCartePreseS2.getY() + 75, 75, 25);
        String spazio1 = "";
        String spazio2 = "";
        if(puntiS1 < 10)
            spazio1 = "  ";
        else if(puntiS1 < 100)
            spazio1 = " ";
        else
            spazio1 = "";
        if(puntiS2 < 10)
            spazio2 = "  ";
        else if(puntiS2 < 100)
            spazio2 = " ";
        else spazio2 = "";
        g.drawString(spazio1 + puntiS1, labelCartePreseS1.getX() + 50, labelCartePreseS1.getY() + 95);
        g.drawString(spazio2 + puntiS2, labelCartePreseS2.getX() + 50, labelCartePreseS2.getY() + 95);
        repaint();
    }
    
    public void generaThreadIniziale(){
        System.out.println("CLIENT\tDo le carte");
        animazioneIniziale = new Thread(){
            @Override
            public void run(){
                //aspetto che il gioco sia visibile
//                while(!game.isShowing()){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {}
//                }
//                System.out.println("CLIENT\tGame is showing");
                daiCarte();
            }
            
            private void daiCarte(){
                
                boolean g11 = false;
                boolean g12 = false;
                boolean g13 = false;
                boolean g21 = false;
                boolean g22 = false;
                boolean g23 = false;
                boolean g31 = false;
                boolean g32 = false;
                boolean g33 = false;
                boolean g41 = false;
                boolean g42 = false;
                boolean g43 = false;
                
                System.out.println("CLIENT\tEntro in daiCarte()");
                if(player.equals("g1")){
                    System.out.println("Player è uguale a 1");
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
                        if(!pescaG21){ g21 = false; g31 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g31){
                        pescaG31 = true;
                        repaint();
                        if(!pescaG31){ g31 = false; g41 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g41){
                        pescaG41 = true;
                        repaint();
                        if(!pescaG41){ g41 = false; g12 = true; }
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
                        if(!pescaG22){ g22 = false; g32 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g32){
                        pescaG32 = true;
                        repaint();
                        if(!pescaG32){ g32 = false; g42 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g42){
                        pescaG42 = true;
                        repaint();
                        if(!pescaG42){ g42 = false; g13 = true; }
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
                        if(!pescaG23){ g23 = false; g33 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g33){
                        pescaG33 = true;
                        repaint();
                        if(!pescaG33){ g33 = false; g43 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g43){
                        pescaG43 = true;
                        repaint();
                        if(!pescaG43){ g43 = false; }
                    }
                } else if (player.equals("g2")) {
                    System.out.println("Player è uguale a 2");
                    g41 = true;
                    //ripristino le coordinate della carta dal mazzo
                    game.getMazzoCoordinates();
                    //do la carta al G12
                    while(g41){
                        pescaG41 = true;
                        //parte l'animazione
                        repaint();
                        //quando ha finito di dare la carta al G21, la da al G11
                        if(!pescaG41){ g41 = false; g11 = true; }
                    }
                    //si ripete per ogni carta dei giocatori
                    game.getMazzoCoordinates();
                    while(g11){
                        pescaG11 = true;
                        repaint();
                        if(!pescaG11){ g11 = false; g21 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g21){
                        pescaG21 = true;
                        repaint();
                        if(!pescaG21){ g21 = false; g31 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g31){
                        pescaG31 = true;
                        repaint();
                        if(!pescaG31){ g31 = false; g42 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g42){
                        pescaG42 = true;
                        repaint();
                        if(!pescaG42){ g42 = false; g12 = true; }
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
                        if(!pescaG22){ g22 = false; g32 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g32){
                        pescaG32 = true;
                        repaint();
                        if(!pescaG32){ g32 = false; g43 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g43){
                        pescaG43 = true;
                        repaint();
                        if(!pescaG43){ g43 = false; g13 = true; }
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
                        if(!pescaG23){ g23 = false; g33 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g33){
                        pescaG33 = true;
                        repaint();
                        if(!pescaG33){ g33 = false; }
                    }
                } else if (player.equals("g3")) {
                    System.out.println("Player è uguale a 3");
                    g31 = true;
                    //ripristino le coordinate della carta dal mazzo
                    game.getMazzoCoordinates();
                    //do la carta al G12
                    while(g31){
                        pescaG31 = true;
                        //parte l'animazione
                        repaint();
                        //quando ha finito di dare la carta al G21, la da al G11
                        if(!pescaG31){ g31 = false; g41 = true; }
                    }
                    //si ripete per ogni carta dei giocatori
                    game.getMazzoCoordinates();
                    while(g41){
                        pescaG41 = true;
                        repaint();
                        if(!pescaG41){ g41 = false; g11 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g11){
                        pescaG11 = true;
                        repaint();
                        if(!pescaG11){ g11 = false; g21 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g21){
                        pescaG21 = true;
                        repaint();
                        if(!pescaG21){ g21 = false; g32 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g32){
                        pescaG32 = true;
                        repaint();
                        if(!pescaG32){ g32 = false; g42 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g42){
                        pescaG42 = true;
                        repaint();
                        if(!pescaG42){ g42 = false; g12 = true; }
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
                        if(!pescaG22){ g22 = false; g33 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g33){
                        pescaG33 = true;
                        repaint();
                        if(!pescaG33){ g33 = false; g43 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g43){
                        pescaG43 = true;
                        repaint();
                        if(!pescaG43){ g43 = false; g13 = true; }
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
                } else if (player.equals("g4")) {
                    System.out.println("Player è uguale a 4");
                    g21 = true;
                    //ripristino le coordinate della carta dal mazzo
                    game.getMazzoCoordinates();
                    //do la carta al G12
                    while(g21){
                        pescaG21 = true;
                        //parte l'animazione
                        repaint();
                        //quando ha finito di dare la carta al G21, la da al G11
                        if(!pescaG21){ g21 = false; g31 = true; }
                    }
                    //si ripete per ogni carta dei giocatori
                    game.getMazzoCoordinates();
                    while(g31){
                        pescaG31 = true;
                        repaint();
                        if(!pescaG31){ g31 = false; g41 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g41){
                        pescaG41 = true;
                        repaint();
                        if(!pescaG41){ g41 = false; g11 = true; }
                    }
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
                        if(!pescaG22){ g22 = false; g32 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g32){
                        pescaG32 = true;
                        repaint();
                        if(!pescaG32){ g32 = false; g42 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g42){
                        pescaG42 = true;
                        repaint();
                        if(!pescaG42){ g42 = false; g12 = true; }
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
                        if(!pescaG23){ g23 = false; g33 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g33){
                        pescaG33 = true;
                        repaint();
                        if(!pescaG33){ g33 = false; g43 = true; }
                    }
                    game.getMazzoCoordinates();
                    while(g43){
                        pescaG43 = true;
                        repaint();
                        if(!pescaG43){ g43 = false; g13 = true; }
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

    public Image getImage(Icon icon) {
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        icon.paintIcon(null, g2, 0, 0);
        return image;
    }

    private void spostaCartaG11() {
        if (cartax < labelCartaGiocataG1.getX()) {
            cartax += 10;
        }
        if (cartay > labelCartaGiocataG1.getY()) {
            cartay -= 10;
        }
        if (cartax >= labelCartaGiocataG1.getX() && cartay <= labelCartaGiocataG1.getY()) {
            cardG11played = false;
            ImageIcon img = new ImageIcon(imageG11);
            labelCartaGiocataG1.setIcon(img);
            protocol.playCard(player, cartaToString(carta1), 1);
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG12() {
        if (cartax < labelCartaGiocataG1.getX()) {
            cartax += 6;
        }
        if (cartay > labelCartaGiocataG1.getY()) {
            cartay -= 11;
        }
        if (cartax >= labelCartaGiocataG1.getX() && cartay <= labelCartaGiocataG1.getY()) {
            cardG12played = false;
            ImageIcon img = new ImageIcon(imageG12);
            labelCartaGiocataG1.setIcon(img);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            cartax = labelCartaG22.getLocationOnScreen().x;
            cartay = labelCartaG22.getLocationOnScreen().y;
            protocol.playCard(player, cartaToString(carta2), 2);
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG13() {
        if (cartax > labelCartaGiocataG1.getX()) {
            cartax -= 5;
        }
        if (cartay > labelCartaGiocataG1.getY()) {
            cartay -= 9;
        }
        if (cartax <= labelCartaGiocataG1.getX() && cartay <= labelCartaGiocataG1.getY()) {
            cardG13played = false;
            ImageIcon img = new ImageIcon(imageG13);
            labelCartaGiocataG1.setIcon(img);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            
            cartax = labelCartaG23.getLocationOnScreen().x;
            cartay = labelCartaG23.getLocationOnScreen().y;
            protocol.playCard(player, cartaToString(carta3), 3);
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }

    private void spostaCartaG21() {
        labelCartaG21.setIcon(null);
        if (cartax < labelCartaGiocataG2.getX()) {
            cartax += 11;
        }
        if (cartay < labelCartaGiocataG2.getY()) {
            cartay += 9;
        }
        if (cartax >= labelCartaGiocataG2.getX() && cartay >= labelCartaGiocataG2.getY()) {
            cardG21played = false;
            ImageIcon img = new ImageIcon(imageG21);
            labelCartaGiocataG2.setIcon(img);
            cartax = labelCartaG31.getLocationOnScreen().x;
            cartay = labelCartaG31.getLocationOnScreen().y;
            repaint();
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
            cartay += 9;
        }
        if (cartax >= labelCartaGiocataG2.getX() && cartay >= labelCartaGiocataG2.getY()) {
            cardG22played = false;
            ImageIcon img = new ImageIcon(imageG22);
            labelCartaGiocataG2.setIcon(img);
            cartax = labelCartaG32.getLocationOnScreen().x;
            cartay = labelCartaG32.getLocationOnScreen().y;
            repaint();
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
            cartay += 9;
        }
        if (cartax >= labelCartaGiocataG2.getX() && cartay >= labelCartaGiocataG2.getY()) {
            cardG23played = false;
            ImageIcon img = new ImageIcon(imageG23);
            labelCartaGiocataG2.setIcon(img);
            cartax = labelCartaG33.getLocationOnScreen().x;
            cartay = labelCartaG33.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void spostaCartaG31(){
         labelCartaG31.setIcon(null);
        if (cartax < labelCartaGiocataG3.getX()) {
            cartax += 10;
        }
        if (cartay < labelCartaGiocataG3.getY()) {
            cartay += 10;
        }
        if (cartax >= labelCartaGiocataG3.getX() && cartay >= labelCartaGiocataG3.getY()) {
            cardG31played = false;

            ImageIcon img = new ImageIcon(imageG31);
            labelCartaGiocataG3.setIcon(img);
            cartax = labelCartaG31.getLocationOnScreen().x;
            cartay = labelCartaG31.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void spostaCartaG32(){
         labelCartaG32.setIcon(null);
        if (cartax > labelCartaGiocataG3.getX()) {
            cartax -= 5;
        }
        if (cartay < labelCartaGiocataG3.getY()) {
            cartay += 4;
        }
        if (cartax >= labelCartaGiocataG3.getX() && cartay >= labelCartaGiocataG3.getY()) {
            cardG32played = false;

            ImageIcon img = new ImageIcon(imageG32);
            labelCartaGiocataG3.setIcon(img);
            cartax = labelCartaG32.getLocationOnScreen().x;
            cartay = labelCartaG32.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void spostaCartaG33(){
         labelCartaG33.setIcon(null);
        if (cartax > labelCartaGiocataG3.getX()) {
            cartax -= 5;
        }
        if (cartay < labelCartaGiocataG3.getY()) {
            cartay += 4;
        }
        if (cartax >= labelCartaGiocataG3.getX() && cartay >= labelCartaGiocataG3.getY()) {
            cardG33played = false;

            ImageIcon img = new ImageIcon(imageG33);
            labelCartaGiocataG3.setIcon(img);
            cartax = labelCartaG33.getLocationOnScreen().x;
            cartay = labelCartaG33.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void spostaCartaG41(){
         labelCartaG41.setIcon(null);
        if (cartax > labelCartaGiocataG4.getX()) {
            cartax -= 11;
        }
        if (cartay < labelCartaGiocataG4.getY()) {
            cartay += 9;
        }
        if (cartax <= labelCartaGiocataG4.getX() && cartay >= labelCartaGiocataG4.getY()) {
            cardG41played = false;
            ImageIcon img = new ImageIcon(imageG41);
            labelCartaGiocataG4.setIcon(img);
            cartax = labelCartaG42.getLocationOnScreen().x;
            cartay = labelCartaG42.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void spostaCartaG42(){
        labelCartaG42.setIcon(null);
        if (cartax > labelCartaGiocataG4.getX()) {
            cartax -= 7;
        }
        if (cartay < labelCartaGiocataG4.getY()) {
            cartay += 9;
        }
        if (cartax <= labelCartaGiocataG4.getX() && cartay >= labelCartaGiocataG4.getY()) {
            cardG42played = false;

            ImageIcon img = new ImageIcon(imageG42);
            labelCartaGiocataG4.setIcon(img);
            cartax = labelCartaG42.getLocationOnScreen().x;
            cartay = labelCartaG42.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void spostaCartaG43(){
        labelCartaG43.setIcon(null);
        if (cartax > labelCartaGiocataG4.getX()) {
            cartax -= 7;
        }
        if (cartay < labelCartaGiocataG4.getY()) {
            cartay += 9;
        }
        if (cartax <= labelCartaGiocataG4.getX() && cartay >= labelCartaGiocataG4.getY()) {
            cardG43played = false;
            ImageIcon img = new ImageIcon(imageG43);
            labelCartaGiocataG4.setIcon(img);
            cartax = labelCartaG43.getLocationOnScreen().x;
            cartay = labelCartaG43.getLocationOnScreen().y;
            repaint();
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
    }
    
    private void prendiG1(){
        pulisciCarteGiocate();
        if(cartaGiocataG1x < labelCartePreseS1.getLocationOnScreen().x){
            cartaGiocataG1x += 6;
        }
        if(cartaGiocataG1y < labelCartePreseS1.getLocationOnScreen().y){
            cartaGiocataG1y += 4;
        }
        if(cartaGiocataG1x >= labelCartePreseS1.getLocationOnScreen().x && 
                cartaGiocataG1y >= labelCartePreseS1.getLocationOnScreen().y){
            prendiG1 = false;
            ImageIcon img = new ImageIcon(horizontalCardBack);
            if(player.equals("g2") || player.equals("g4"))
                disegnaCartePreseG1 = true;
            else
                disegnaCartePreseG2 = true;
        }
    }
    
    private void prendiG2(){
        pulisciCarteGiocate();
        if(cartaGiocataG2x < labelCartePreseS2.getLocationOnScreen().x){
            cartaGiocataG2x += 6;
        }
        if(cartaGiocataG2y > labelCartePreseS2.getLocationOnScreen().y){
            cartaGiocataG2y -= 4;
        }
        if(cartaGiocataG2x >= labelCartePreseS2.getLocationOnScreen().x && 
                cartaGiocataG2y <= labelCartePreseS2.getLocationOnScreen().y){
            prendiG2 = false;
            ImageIcon img = new ImageIcon(horizontalCardBack);
            if(player.equals("g2") || player.equals("g4"))
                disegnaCartePreseG2 = true;
            else
                disegnaCartePreseG1 = true;
        }
    }
    
    private void prendiG3(){
        pulisciCarteGiocate();
        if(cartaGiocataG3x < labelCartePreseS1.getLocationOnScreen().x){
            cartaGiocataG3x += 6;
        }
        if(cartaGiocataG3y > labelCartePreseS1.getLocationOnScreen().y){
            cartaGiocataG3y -= 4;
        }
        if(cartaGiocataG3x >= labelCartePreseS1.getLocationOnScreen().x && 
                cartaGiocataG3y <= labelCartePreseS1.getLocationOnScreen().y){
            prendiG3 = false;
            ImageIcon img = new ImageIcon(horizontalCardBack);
            if(player.equals("g2") || player.equals("g4"))
                disegnaCartePreseG1 = true;
            else
                disegnaCartePreseG2 = true;
        }
    }
    
    private void prendiG4(){
        pulisciCarteGiocate();
        if(cartaGiocataG4x < labelCartePreseS2.getLocationOnScreen().x){
            cartaGiocataG4x += 6;
        }
        if(cartaGiocataG4y > labelCartePreseS2.getLocationOnScreen().y){
            cartaGiocataG4y -= 4;
        }
        if(cartaGiocataG4x >= labelCartePreseS2.getLocationOnScreen().x && 
                cartaGiocataG4y <= labelCartePreseS2.getLocationOnScreen().y){
            prendiG4 = false;
            ImageIcon img = new ImageIcon(horizontalCardBack);
            if(player.equals("g2") || player.equals("g4"))
                labelCartePreseS2.setIcon(img);
            else
                labelCartePreseS1.setIcon(img);
        }
    }

    private void pulisciCarteGiocate() {
        labelCartaGiocataG1.setIcon(null);
        labelCartaGiocataG2.setIcon(null);
        labelCartaGiocataG3.setIcon(null);
        labelCartaGiocataG4.setIcon(null);
    }
    
    private void getPlayedCardsImages() {
        cardG1 = getImage(labelCartaGiocataG1.getIcon());
        cardG2 = getImage(labelCartaGiocataG2.getIcon());
    }
    
    private void getPlayedCardsCoordinates(){
        cartaGiocataG2x = labelCartaGiocataG2.getLocationOnScreen().x;
        cartaGiocataG2y = labelCartaGiocataG2.getLocationOnScreen().y;
        cartaGiocataG1x = labelCartaGiocataG1.getLocationOnScreen().x;
        cartaGiocataG1y = labelCartaGiocataG1.getLocationOnScreen().y;
    }
    
    public void getMazzoCoordinates(){
        //ripristina le coordinate
        cartaPescataX = labelMazzo.getLocationOnScreen().x;
        cartaPescataY = labelMazzo.getLocationOnScreen().y;
    }
    
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
        labelCartaG31 = new javax.swing.JLabel();
        labelCartaG32 = new javax.swing.JLabel();
        labelCartaG33 = new javax.swing.JLabel();
        labelCartaG21 = new javax.swing.JLabel();
        labelCartaG22 = new javax.swing.JLabel();
        labelCartaG23 = new javax.swing.JLabel();
        labelCartaG41 = new javax.swing.JLabel();
        labelCartaG42 = new javax.swing.JLabel();
        labelCartaG43 = new javax.swing.JLabel();
        labelCartePreseS2 = new javax.swing.JLabel();
        labelCartePreseS1 = new javax.swing.JLabel();
        labelMazzo = new javax.swing.JLabel();
        labelBriscola = new javax.swing.JLabel();
        labelCartaGiocataG1 = new javax.swing.JLabel();
        labelCartaGiocataG3 = new javax.swing.JLabel();
        labelCartaGiocataG2 = new javax.swing.JLabel();
        labelCartaGiocataG4 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1195, 1128));
        setLayout(null);

        labelCartaG11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG11MouseClicked(evt);
            }
        });
        add(labelCartaG11);
        labelCartaG11.setBounds(380, 560, 140, 204);

        labelCartaG12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG12MouseClicked(evt);
            }
        });
        add(labelCartaG12);
        labelCartaG12.setBounds(530, 560, 140, 204);

        labelCartaG13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG13MouseClicked(evt);
            }
        });
        add(labelCartaG13);
        labelCartaG13.setBounds(680, 560, 140, 204);
        add(labelCartaG31);
        labelCartaG31.setBounds(380, 10, 140, 204);
        add(labelCartaG32);
        labelCartaG32.setBounds(530, 10, 140, 204);
        add(labelCartaG33);
        labelCartaG33.setBounds(680, 10, 140, 204);
        add(labelCartaG21);
        labelCartaG21.setBounds(40, 280, 140, 204);
        add(labelCartaG22);
        labelCartaG22.setBounds(80, 280, 140, 204);
        add(labelCartaG23);
        labelCartaG23.setBounds(120, 280, 140, 204);
        add(labelCartaG41);
        labelCartaG41.setBounds(930, 280, 140, 204);
        add(labelCartaG42);
        labelCartaG42.setBounds(970, 280, 140, 204);
        add(labelCartaG43);
        labelCartaG43.setBounds(1010, 280, 140, 204);
        add(labelCartePreseS2);
        labelCartePreseS2.setBounds(40, 90, 204, 140);
        add(labelCartePreseS1);
        labelCartePreseS1.setBounds(910, 590, 204, 140);
        add(labelMazzo);
        labelMazzo.setBounds(930, 90, 204, 140);

        labelBriscola.setPreferredSize(new java.awt.Dimension(140, 102));
        add(labelBriscola);
        labelBriscola.setBounds(960, 20, 140, 204);

        labelCartaGiocataG1.setPreferredSize(new java.awt.Dimension(140, 204));
        add(labelCartaGiocataG1);
        labelCartaGiocataG1.setBounds(600, 280, 140, 204);
        add(labelCartaGiocataG3);
        labelCartaGiocataG3.setBounds(450, 280, 140, 204);

        labelCartaGiocataG2.setToolTipText("");
        add(labelCartaGiocataG2);
        labelCartaGiocataG2.setBounds(300, 280, 140, 204);

        labelCartaGiocataG4.setPreferredSize(new java.awt.Dimension(140, 204));
        add(labelCartaGiocataG4);
        labelCartaGiocataG4.setBounds(750, 280, 140, 204);
    }// </editor-fold>//GEN-END:initComponents

    private void labelCartaG11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG11MouseClicked
        // TODO add your handling code here:
        if(turno.equals(player)){
            if(labelCartaG11.getIcon() != null){
                cartax = labelCartaG11.getLocationOnScreen().x;
                cartay = labelCartaG11.getLocationOnScreen().y;
                imageG11 = getImage(labelCartaG11.getIcon());
                labelCartaG11.setIcon(null);
                cardG11played = true;
                System.out.println("PREMUTO");
                repaint();
            }
        }
    }//GEN-LAST:event_labelCartaG11MouseClicked

    private void labelCartaG12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG12MouseClicked
        // TODO add your handling code here:
        if(turno.equals(player)){
            if(labelCartaG11.getIcon() != null){
                cartax = labelCartaG12.getLocationOnScreen().x;
                cartay = labelCartaG12.getLocationOnScreen().y;
                imageG12 = getImage(labelCartaG12.getIcon());
                labelCartaG12.setIcon(null);
                cardG12played = true;
                repaint();
            }
        }
    }//GEN-LAST:event_labelCartaG12MouseClicked

    private void labelCartaG13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG13MouseClicked
        // TODO add your handling code here:
        if(turno.equals(player)){
            if(labelCartaG13.getIcon() != null){
                cartax = labelCartaG13.getLocationOnScreen().x;
                cartay = labelCartaG13.getLocationOnScreen().y;
                imageG13 = getImage(labelCartaG13.getIcon());
                labelCartaG13.setIcon(null);
                cardG13played = true;
                repaint();
            }
        }
    }//GEN-LAST:event_labelCartaG13MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelBriscola;
    public javax.swing.JLabel labelCartaG11;
    public javax.swing.JLabel labelCartaG12;
    public javax.swing.JLabel labelCartaG13;
    public javax.swing.JLabel labelCartaG21;
    public javax.swing.JLabel labelCartaG22;
    public javax.swing.JLabel labelCartaG23;
    public javax.swing.JLabel labelCartaG31;
    public javax.swing.JLabel labelCartaG32;
    public javax.swing.JLabel labelCartaG33;
    public javax.swing.JLabel labelCartaG41;
    public javax.swing.JLabel labelCartaG42;
    public javax.swing.JLabel labelCartaG43;
    public javax.swing.JLabel labelCartaGiocataG1;
    public javax.swing.JLabel labelCartaGiocataG2;
    public javax.swing.JLabel labelCartaGiocataG3;
    public javax.swing.JLabel labelCartaGiocataG4;
    private javax.swing.JLabel labelCartePreseS1;
    private javax.swing.JLabel labelCartePreseS2;
    private javax.swing.JLabel labelMazzo;
    // End of variables declaration//GEN-END:variables
}
