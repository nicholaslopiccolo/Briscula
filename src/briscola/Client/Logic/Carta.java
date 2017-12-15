/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.Logic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author g.evangelista
 */
public class Carta {
    
    private int numero;
    private int punti = 0;
    private int valore = 0;
    private String seme;
    private boolean briscola;
    private BufferedImage image;
    
    public Carta(int n, String c) throws IOException{
        this.numero = n;
        setValore(n);
        setPunti(n);
        setSeme(c);
        setImage(numero, seme);
    }

    public void setValore(int numero) {
        if(numero == 1) this.valore = 10;
        if(numero == 3) this.valore = 9;
        if(numero == 10) this.valore = 8;
        if(numero == 9) this.valore = 7;
        if(numero == 8) this.valore = 6;
        if(numero == 7) this.valore = 5;
        if(numero == 6) this.valore = 4;
        if(numero == 5) this.valore = 3;
        if(numero == 4) this.valore = 2;
        if(numero == 2) this.valore = 1;
                
    }

    public void setSeme(String c) {
        if(c.equals("b")) this.seme = "Bastoni";
        else if (c.equals("d")) this.seme = "Denari";
        else if (c.equals("s")) this.seme = "Spade";
        else if (c.equals("c")) this.seme = "Coppe";
    }
    
    private void setPunti(int numero) {
        if(numero == 1) this.punti = 11;
        else if (numero == 3) this.punti = 10;
        else if (numero == 8) this.punti = 2;
        else if (numero == 9) this.punti = 3;
        else if (numero == 10) this.punti = 4;
    }
    
    public int getPunti() {
        return punti;
    }

    public int getValore() {
        return punti;
    }

    public int getNumero() {
        return numero;
    }

    public String getSeme() {
        return seme;
    }
    
    public String getSmallSeme(){
        return seme.substring(0,1).toLowerCase();
    }
    
    public void setBriscola(boolean b){
        this.briscola = b;
    }
    
    public boolean isBriscola(){
        return this.briscola;
    }
    
    private void setImage(int numero, String seme) throws IOException{
        String n = "";
        if(numero < 10)
            n = "0"+numero;
        else
            n = ""+numero;
        if(this.seme.equals("Denari"))
            image = ImageIO.read(this.getClass().getResource("../Immagini/" + n + "d.png"));
        else if(this.seme.equals("Bastoni"))
            image = ImageIO.read(this.getClass().getResource("../Immagini/" + n + "b.png"));
        else if(this.seme.equals("Spade"))
            image = ImageIO.read(this.getClass().getResource("../Immagini/" + n + "s.png"));
        else if(this.seme.equals("Coppe"))
            image = ImageIO.read(this.getClass().getResource("../Immagini/" + n + "c.png"));
        
    }
    
    public Image getImage(){
        return image;
    }
    
}
