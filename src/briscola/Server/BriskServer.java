/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server;


import briscola.Server.LogicApplicativa.MainBrain;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author x.ruan
 */
public class BriskServer extends Thread{
    
    private static final int PORT = 4444;
    
    public static ArrayList<User> clientsConnected;
    public static ArrayList clientsNames;
    
    private static Socket clientSocket;
    
    private int nPlayers = 0;
    
    
    public BriskServer(int players){
        nPlayers = players;
        start();
    }
    
    
    @Override
    public void run(){
       try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("SERVER\tNew server started. Socket : " + serverSocket);
            clientsConnected = new ArrayList();
            clientsNames = new ArrayList();
            int i = 0;
            while(i < nPlayers){
                clientSocket = serverSocket.accept();
                System.out.println("SERVER\tConnection accepted: " + clientSocket);
                User player = new User(clientSocket);
                clientsConnected.add(player);
                i++;
            }
        } catch (IOException ex) {
            System.out.println("SERVER\tAccept failed.");
            System.exit(1);
        }
        System.out.println("\n\nSERVER\tGame ready to Start");
        try {
            MainBrain mb = new MainBrain(nPlayers, clientsConnected);
        } catch (IOException ex) {}
    }
}
