/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server.LogicApplicativa;


import briscola.Server.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Gabriele
 */
public class Writer extends Thread{
    
    private InputStreamReader reader;
    private BufferedReader myInput;
    private PrintWriter writer;
    private int turno = 0;
    private ArrayList<User> players;
    
    public Writer(ArrayList<User> players){
        reader = new InputStreamReader (System.in);
        myInput = new BufferedReader (reader);
        this.players = players;
        //writer = new PrintWriter(fromClientSocket.getOutputStream());
        
    }
    
    public String gioca() throws IOException{
        String carta = players.get(0).connectedSocket.readFromSocket();
        /*String c = "";
        if(turno%2 == 0) c = "g1";
        else c = "g2";
        System.out.print("Gioca" + ":  ");
        c += myInput.readLine();
        turno++;*/
        return carta;
    }
    
    public void writeTo(String msg, String target) throws IOException{
        User user;
        user = players.get(Integer.parseInt(target.substring(0,1)) - 1);
        writer = new PrintWriter(user.connectedSocket.fromClientSocket.getOutputStream());
        writer.println(msg);
        writer.flush();
    }
}
