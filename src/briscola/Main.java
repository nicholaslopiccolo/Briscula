/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola;


import briscola.Client.GUI.JPanelAttesa;
import briscola.Client.GUI.JPanelLogin;
import briscola.Client.GUI.New2PGame;
import briscola.Client.GUI.New4PGame;
import briscola.Client.Logic.*;
import briscola.Server.BriskServer;
import java.io.File;
import java.io.IOException;
import java.util.Random;
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
    public static BriskServer serverThread;
    public static JPanelAttesa attesa;
    public static int nPlayers;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException  {
        // TODO code application logic here
        clientThread = new ClientThread();
        login = new JPanelLogin(clientThread);
        new2PGame  = new New2PGame(clientThread);
        new4PGame  = new New4PGame(clientThread);
        attesa = new JPanelAttesa();
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ClientProtocol p = new ClientProtocol(clientThread);
        menu.setUndecorated(true);
        menu.pack();
        menu.add(login);
        menu.setVisible(true);
        menu.setTitle("BRISCULA");
    
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


