/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author t.erra
 */
public class IstruzioneCarta extends JLabel {
    private BufferedImage [] cartaIstruzione;
    private BufferedImage image ;
    private int cont = 0;
    
    public IstruzioneCarta(){
        
        cartaIstruzione = new BufferedImage[3];
        
        try {
            cartaIstruzione[0] = ImageIO.read(this.getClass().getResource("../Immagini/1.png"));
            cartaIstruzione[1] = ImageIO.read(this.getClass().getResource("../Immagini/2.png"));
            cartaIstruzione[2] = ImageIO.read(this.getClass().getResource("../Immagini/3.png"));
                
            } catch (IOException ex) {
                System.out.println("Errore caricamento immagini carta istruzioni");
            }
        image = cartaIstruzione[cont];
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0,getWidth(), getHeight(),null);
    }
    
    public void caricaImmagine(int n){
        image = cartaIstruzione[n];
    }
    
    public int immagineSuccessiva(){
        cont++;
        System.out.print(cont);
        image = cartaIstruzione[cont];
        validate();
        repaint();
        
        
        return cont;
    }
    
    public int immaginePrecedente() {
        cont--;
        System.out.print(cont);
        image = cartaIstruzione[cont];
        validate();
        repaint();
        
        return cont;
    }
    
}
