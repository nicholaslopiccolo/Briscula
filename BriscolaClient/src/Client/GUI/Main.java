/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.GUI;

import Client.ClientThread;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

/**
 *
 * @author t.erra
 */
public class Main {
   
    public static JFrame menu = new JFrame();
    public static JPanelLogin login;
    public static New2PGame new2PGame;
    public static New4PGame new4PGame;
    public static ClientThread clientThread;
    public static JPanelAttesa attesa = new JPanelAttesa();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here
        clientThread = new ClientThread();
        login = new JPanelLogin();
        new2PGame  = new New2PGame();
        new4PGame  = new New4PGame();
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menu.setUndecorated(true);
        menu.pack();
        menu.add(login);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(3);
        try {
            playMusic();
        } 
        catch (UnsupportedAudioFileException ex) {} 
        catch (LineUnavailableException ex) {} 
        catch (IOException ex) {}
    }
    
    private static void playMusic() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        Random r = new Random();
        int song = r.nextInt(1);
        song++;
        File file = new File("src/Client/Sounds/bgm" + song + ".wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }
}


