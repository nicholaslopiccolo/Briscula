/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.GUI;

import java.awt.Color;
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
    private boolean cardG11played = false;
    private boolean cardG12played = false;
    private boolean cardG13played = false;
    private boolean cardG21played = false;
    private boolean cardG22played = false;
    private boolean cardG23played = false;
    private boolean cardG31played = false;
    private boolean cardG32played = false;
    private boolean cardG33played = false;
    private boolean cardG41played = false;
    private boolean cardG42played = false;
    private boolean cardG43played = false;
    
    private boolean prendiG1 = false;
    private boolean prendiG2 = false;
    private boolean prendiCartaG1 = false;
    private boolean prendiCartaG2 = false;
    
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

    private Image imageG11;
    private Image imageG12;
    private Image imageG13;
    private Image imageG21;
    private Image imageG22;
    private Image imageG23;
    private Image imageG31;
    private Image imageG32;
    private Image imageG33;
    private Image imageG41;
    private Image imageG42;
    private Image imageG43;
    private Image cardG1;
    private Image cardG2;
    private Image cardBack;
    
    private int cartax;
    private int cartay;
    private int cartaPescataX;
    private int cartaPescataY;
    private int cartaGiocataG1x;
    private int cartaGiocataG1y;
    private int cartaGiocataG2x;
    private int cartaGiocataG2y;
    private New4PGame game;
    
    private  BufferedImage [] sfondoTav;
    private  BufferedImage image;
    private  int sceltaTav;
    private  Random random;

    public New4PGame() throws IOException {
        initComponents();
        game = this;
        
        this.setBackground(Color.black);
        cardBack = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG11 = ImageIO.read(this.getClass().getResource("../Immagini/01d.png"));
        imageG12 = ImageIO.read(this.getClass().getResource("../Immagini/02d.png"));
        imageG13 = ImageIO.read(this.getClass().getResource("../Immagini/03d.png"));
        imageG21 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG22 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG23 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG31 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG32 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG33 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG41 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG42 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        imageG43 = ImageIO.read(this.getClass().getResource("../Immagini/yugiohVerticale.png"));
        random = new Random();
        sceltaTav = random.nextInt(4);
        System.out.println(sceltaTav);
        
        BufferedImage [] sfondoTav = new BufferedImage[4];
        
        try {
            sfondoTav[0] = ImageIO.read(this.getClass().getResource("../Immagini/tav1.png"));
            sfondoTav[1] = ImageIO.read(this.getClass().getResource("../Immagini/tav2.png"));
            sfondoTav[2] = ImageIO.read(this.getClass().getResource("../Immagini/tav3.png"));
            sfondoTav[3] = ImageIO.read(this.getClass().getResource("../Immagini/tav4.png"));
        } catch (IOException ex) {
            Logger.getLogger(JPanelLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        image=sfondoTav[sceltaTav];
        Thread animazioneIniziale = new Thread(){
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
                boolean g11 = true;
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
                //ripristino le coordinate della carta dal mazzo
                getMazzoCoordinates();
                //do la carta al G11
                while(g11){
                    pescaG11 = true;
                    //parte l'animazione
                    repaint();
                    //quando ha finito di dare la carta al G11, la da al G21
                    if(!pescaG11){ g11 = false; g21 = true; }
                }
                //si ripete per ogni carta dei giocatori
                getMazzoCoordinates();
                while(g21){
                    pescaG21 = true;
                    repaint();
                    if(!pescaG21){ g21 = false; g31 = true; }
                }
                getMazzoCoordinates();
                while(g31){
                    pescaG31 = true;
                    repaint();
                    if(!pescaG31){ g31 = false; g41 = true; }
                }
                getMazzoCoordinates();
                while(g41){
                    pescaG41 = true;
                    repaint();
                    if(!pescaG41){ g41 = false; g12 = true; }
                }
                getMazzoCoordinates();
                while(g12){
                    pescaG12 = true;
                    repaint();
                    if(!pescaG12){ g12 = false; g22 = true; }
                }
                getMazzoCoordinates();
                while(g22){
                    pescaG22 = true;
                    repaint();
                    if(!pescaG22){ g22 = false; g32 = true; }
                }
                getMazzoCoordinates();
                while(g32){
                    pescaG32 = true;
                    repaint();
                    if(!pescaG32){ g32 = false; g42 = true; }
                }
                getMazzoCoordinates();
                while(g42){
                    pescaG42 = true;
                    repaint();
                    if(!pescaG42){ g42 = false; g13 = true; }
                }
                getMazzoCoordinates();
                while(g13){
                    pescaG13 = true;
                    repaint();
                    if(!pescaG13){ g13 = false; g23 = true; }
                }
                getMazzoCoordinates();
                while(g23){
                    pescaG23 = true;
                    repaint();
                    if(!pescaG23){ g23 = false; g33 = true; }
                }
                getMazzoCoordinates();
                while(g33){
                    pescaG33 = true;
                    repaint();
                    if(!pescaG33){ g33 = false; g43 = true; }
                }
                getMazzoCoordinates();
                while(g43){
                    pescaG43 = true;
                    repaint();
                    if(!pescaG43){ g43 = false; }
                }
            }
            
            private void getMazzoCoordinates(){
                //ripristina le coordinate
                cartaPescataX = labelMazzo.getLocationOnScreen().x;
                cartaPescataY = labelMazzo.getLocationOnScreen().y;
            }
        };
        //animazioneIniziale.start(); 
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,getWidth(), getHeight(), null);
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
            g.drawImage(imageG21, cartax, cartay, this);
            repaint();
        }
        if (cardG22played) {
            spostaCartaG22();
            g.drawImage(imageG22, cartax, cartay, this);
            repaint();
        }
        if (cardG23played) {
            spostaCartaG23();
            g.drawImage(imageG23, cartax, cartay, this);
            repaint();
        }
        if (cardG31played) {
            spostaCartaG31();
            g.drawImage(imageG31, cartax, cartay, this);
            repaint();
        }
        if (cardG32played) {
            spostaCartaG32();
            g.drawImage(imageG32, cartax, cartay, this);
            repaint();
        }
        if (cardG33played) {
            spostaCartaG33();
            g.drawImage(imageG33, cartax, cartay, this);
            repaint();
        }
        if (cardG41played) {
            spostaCartaG41();
            g.drawImage(imageG41, cartax, cartay, this);
            repaint();
        }
        if (cardG42played) {
            spostaCartaG42();
            g.drawImage(imageG42, cartax, cartay, this);
            repaint();
        }
        if (cardG43played) {
            spostaCartaG43();
            g.drawImage(imageG43, cartax, cartay, this);
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
    }

    private Image getImage(Icon icon) {
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            ImageIcon img = new ImageIcon(imageG11);
            labelCartaGiocataG1.setIcon(img);
            cartax = labelCartaG21.getLocationOnScreen().x;
            cartay = labelCartaG21.getLocationOnScreen().y;
            cardG21played = true;
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
            cardG22played = true;
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
            cardG23played = true;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG31.getLocationOnScreen().x;
            cartay = labelCartaG31.getLocationOnScreen().y;
            cardG31played = true;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG32.getLocationOnScreen().x;
            cartay = labelCartaG32.getLocationOnScreen().y;
            cardG32played = true;
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
            labelCartaGiocataG3.setIcon(img);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG33.getLocationOnScreen().x;
            cartay = labelCartaG33.getLocationOnScreen().y;
            cardG33played = true;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG41.getLocationOnScreen().x;
            cartay = labelCartaG41.getLocationOnScreen().y;
            cardG41played = true;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG42.getLocationOnScreen().x;
            cartay = labelCartaG42.getLocationOnScreen().y;
            cardG42played = true;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG43.getLocationOnScreen().x;
            cartay = labelCartaG43.getLocationOnScreen().y;
            cardG43played = true;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG12.getLocationOnScreen().x;
            cartay = labelCartaG12.getLocationOnScreen().y;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG13.getLocationOnScreen().x;
            cartay = labelCartaG13.getLocationOnScreen().y;
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
            cartax = labelCartaG11.getLocationOnScreen().x;
            cartay = labelCartaG11.getLocationOnScreen().y;
            repaint();
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
        if (cartaPescataY < labelCartaG21.getY()) {
            cartaPescataY += 8;
        }
        if (cartaPescataX <= labelCartaG21.getX() && cartaPescataY >= labelCartaG21.getY()) {
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
        if (cartaPescataY < labelCartaG22.getY()) {
            cartaPescataY += 5;
        }
        if (cartaPescataX <= labelCartaG22.getX() && cartaPescataY >= labelCartaG22.getY()) {
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
        if (cartaPescataY < labelCartaG23.getY()) {
            cartaPescataY += 10;
        }
        if (cartaPescataX <= labelCartaG23.getX() && cartaPescataY >= labelCartaG23.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG23.setIcon(img);
            pescaG23 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void pescaCartaG31() {
        if (cartaPescataX > labelCartaG31.getX()) {
            cartaPescataX -= 5;
        }
        if (cartaPescataY > labelCartaG31.getY()) {
            cartaPescataY -= 10;
        }
        if (cartaPescataX <= labelCartaG31.getX() && cartaPescataY <= labelCartaG31.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG31.setIcon(img);
            pescaG31 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void pescaCartaG32() {
        if (cartaPescataX > labelCartaG32.getX()) {
            cartaPescataX -= 5;
        }
        if (cartaPescataY > labelCartaG32.getY()) {
            cartaPescataY -= 10;
        }
        if (cartaPescataX <= labelCartaG32.getX() && cartaPescataY <= labelCartaG32.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG32.setIcon(img);
            pescaG32 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void pescaCartaG33() {
        if (cartaPescataX > labelCartaG33.getX()) {
            cartaPescataX -= 5;
        }
        if (cartaPescataY > labelCartaG33.getY()) {
            cartaPescataY -= 10;
        }
        if (cartaPescataX <= labelCartaG33.getX() && cartaPescataY <= labelCartaG33.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG33.setIcon(img);
            pescaG33 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void pescaCartaG41() {
        if (cartaPescataX < labelCartaG41.getX()) {
            cartaPescataX += 5;
        }
        if (cartaPescataY < labelCartaG33.getY()) {
            cartaPescataY += 10;
        }
        if (cartaPescataX >= labelCartaG33.getX() && cartaPescataY >= labelCartaG33.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG41.setIcon(img);
            pescaG41 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void pescaCartaG42() {
        if (cartaPescataX < labelCartaG42.getX()) {
            cartaPescataX += 5;
        }
        if (cartaPescataY < labelCartaG42.getY()) {
            cartaPescataY += 10;
        }
        if (cartaPescataX >= labelCartaG42.getX() && cartaPescataY >= labelCartaG42.getY()) {
            ImageIcon img = new ImageIcon(cardBack);
            labelCartaG42.setIcon(img);
            pescaG42 = false;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void prendiCartaG1(){
        labelCartaGiocataG1.setIcon(null);
        if(cartaGiocataG1x < labelCartaGiocataG2.getLocationOnScreen().x){
            cartaGiocataG1x += 5;
        }
        if(cartaGiocataG1x >= labelCartaGiocataG2.getLocationOnScreen().x){
            prendiCartaG1 = false;
            prendiG2 = true;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
        }
    }
    
    private void prendiCartaG2(){
        labelCartaGiocataG2.setIcon(null);
        if(cartaGiocataG2x > labelCartaGiocataG1.getLocationOnScreen().x){
            cartaGiocataG2x -= 5;
        }
        if(cartaGiocataG2x <= labelCartaGiocataG1.getLocationOnScreen().x){
            prendiCartaG2 = false;
            prendiG1 = true;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {}
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
            ImageIcon img = new ImageIcon(cardBack);
            labelCartePreseS1.setIcon(img);
            pescaG11 = true;
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
            ImageIcon img = new ImageIcon(cardBack);
            labelCartePreseS2.setIcon(img);
        }
    }

    private void pulisciCarteGiocate() {
        labelCartaGiocataG1.setIcon(null);
        labelCartaGiocataG2.setIcon(null);
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

        labelCartaG11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/01d.png"))); // NOI18N
        labelCartaG11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG11MouseClicked(evt);
            }
        });
        add(labelCartaG11);
        labelCartaG11.setBounds(380, 560, 140, 204);

        labelCartaG12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/02d.png"))); // NOI18N
        labelCartaG12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG12MouseClicked(evt);
            }
        });
        add(labelCartaG12);
        labelCartaG12.setBounds(530, 560, 140, 204);

        labelCartaG13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/03d.png"))); // NOI18N
        labelCartaG13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCartaG13MouseClicked(evt);
            }
        });
        add(labelCartaG13);
        labelCartaG13.setBounds(680, 560, 140, 204);

        labelCartaG31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG31);
        labelCartaG31.setBounds(380, 10, 140, 204);

        labelCartaG32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG32);
        labelCartaG32.setBounds(530, 10, 140, 204);

        labelCartaG33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG33);
        labelCartaG33.setBounds(680, 10, 140, 204);

        labelCartaG21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG21);
        labelCartaG21.setBounds(40, 280, 140, 204);

        labelCartaG22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG22);
        labelCartaG22.setBounds(80, 280, 140, 204);

        labelCartaG23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG23);
        labelCartaG23.setBounds(120, 280, 140, 204);

        labelCartaG41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG41);
        labelCartaG41.setBounds(930, 280, 140, 204);

        labelCartaG42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG42);
        labelCartaG42.setBounds(970, 280, 140, 204);

        labelCartaG43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohVerticale.png"))); // NOI18N
        add(labelCartaG43);
        labelCartaG43.setBounds(1010, 280, 140, 204);

        labelCartePreseS2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohOrrizzontale.png"))); // NOI18N
        add(labelCartePreseS2);
        labelCartePreseS2.setBounds(40, 90, 204, 140);

        labelCartePreseS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohOrrizzontale.png"))); // NOI18N
        add(labelCartePreseS1);
        labelCartePreseS1.setBounds(910, 590, 204, 140);

        labelMazzo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/yugiohOrrizzontale.png"))); // NOI18N
        add(labelMazzo);
        labelMazzo.setBounds(930, 90, 204, 140);

        labelBriscola.setIcon(new javax.swing.ImageIcon(getClass().getResource("/briscola/Client/Immagini/04d.png"))); // NOI18N
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
        cartax = labelCartaG11.getLocationOnScreen().x;
        cartay = labelCartaG11.getLocationOnScreen().y;
        imageG11 = getImage(labelCartaG11.getIcon());
        labelCartaG11.setIcon(null);
        cardG11played = true;
        repaint();
    }//GEN-LAST:event_labelCartaG11MouseClicked

    private void labelCartaG12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG12MouseClicked
        // TODO add your handling code here:
        cartax = labelCartaG12.getLocationOnScreen().x;
        cartay = labelCartaG12.getLocationOnScreen().y;
        imageG12 = getImage(labelCartaG12.getIcon());
        labelCartaG12.setIcon(null);
        cardG12played = true;
        repaint();
    }//GEN-LAST:event_labelCartaG12MouseClicked

    private void labelCartaG13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCartaG13MouseClicked
        // TODO add your handling code here:
        cartax = labelCartaG13.getLocationOnScreen().x;
        cartay = labelCartaG13.getLocationOnScreen().y;
        imageG13 = getImage(labelCartaG13.getIcon());
        labelCartaG13.setIcon(null);
        cardG13played = true;
        repaint();
    }//GEN-LAST:event_labelCartaG13MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelBriscola;
    private javax.swing.JLabel labelCartaG11;
    private javax.swing.JLabel labelCartaG12;
    private javax.swing.JLabel labelCartaG13;
    private javax.swing.JLabel labelCartaG21;
    private javax.swing.JLabel labelCartaG22;
    private javax.swing.JLabel labelCartaG23;
    private javax.swing.JLabel labelCartaG31;
    private javax.swing.JLabel labelCartaG32;
    private javax.swing.JLabel labelCartaG33;
    private javax.swing.JLabel labelCartaG41;
    private javax.swing.JLabel labelCartaG42;
    private javax.swing.JLabel labelCartaG43;
    private javax.swing.JLabel labelCartaGiocataG1;
    private javax.swing.JLabel labelCartaGiocataG2;
    private javax.swing.JLabel labelCartaGiocataG3;
    private javax.swing.JLabel labelCartaGiocataG4;
    private javax.swing.JLabel labelCartePreseS1;
    private javax.swing.JLabel labelCartePreseS2;
    private javax.swing.JLabel labelMazzo;
    // End of variables declaration//GEN-END:variables
}
