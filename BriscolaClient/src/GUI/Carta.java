/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author t.erra
 */
public class Carta extends JLabel {
    private BufferedImage [] denari;
    private BufferedImage [] bastoni;
    private BufferedImage [] spade;
    private BufferedImage [] coppe;
    
    private BufferedImage [] denariS;
    private BufferedImage [] bastoniS;
    private BufferedImage [] spadeS;
    private BufferedImage [] coppeS;
   
    private BufferedImage image ;

    
    public Carta(String nomeCarta) {
        denari = new BufferedImage[10];
        bastoni = new BufferedImage[10];
        spade = new BufferedImage[10];
        coppe = new BufferedImage[10];
        
        denariS = new BufferedImage[10];
        bastoniS = new BufferedImage[10];
        spadeS= new BufferedImage[10];
        coppeS = new BufferedImage[10];
        
       
        
        /*try {
            
                
            } catch (IOException ex) {
                System.out.println("Errore caricamento carta immagini");
            }*/
        
        int numerocarta = Integer.parseInt(nomeCarta.substring(0, 1));
        String type = nomeCarta.substring(2);
        String typeS = nomeCarta.substring(3);
        
        switch(type){
            case "d":{
                image=denari[numerocarta];
                break;
            }
            
            case "b":{
                image=bastoni[numerocarta];
                break;
            }
            
            case "s":{
                image=spade[numerocarta];
                break;
            }
            
            case "c":{
                image=coppe[numerocarta];
                break;
            }
            
            case "dS":{
                image=denari[numerocarta];
                break;
            }
            
            case "bS":{
                image=bastoni[numerocarta];
                break;
            }
            
            case "sS":{
                image=spade[numerocarta];
                break;
            }
            
            case "cS":{
                image=coppe[numerocarta];
                break;
            }
            
            
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(image, 0, 0,getWidth(), getHeight(),null);
    }
}
