/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.Logic;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author t.erra
 */
public class ClientThread extends Thread {
    private Socket socket = new Socket();
    private BufferedReader in;
    private PrintWriter out;
    
    public ClientThread() {
        System.out.println("CLIENT: ClientThread active");
    }
    
    public void connect(String address, int port){
         try {
            socket = new Socket("127.0.0.1", 4444);
            CentralServerChatter csc = new CentralServerChatter(socket, this);
            //creazione socket
            System.out.println("CLIENT: ClientThread: started");
            System.out.println("CLIENT: Client Socket: " + socket);

            //creazione stream di input da socket
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);

            //creazione sream output da socket
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);
            start();
        } catch (IOException e) {}
    }
    
    public void run() {
        System.out.println("CLIENT: Entro nel run del clientthread\n");
    }
    
    public void writeToServer(String msg){
        out.println(msg);
        out.flush();
    }
}
