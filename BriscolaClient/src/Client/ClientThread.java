/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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
        System.out.println("ClientThread active");
    }
    
    public void connect(String address, int port){
         try {
            socket = new Socket(address, 4444);
            CentralServerChatter csc = new CentralServerChatter(socket);
            //creazione socket
            System.out.println("ClientThread: started");
            System.out.println("Client Socket: " + socket);

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
        System.out.println("Entro nel run del clientthread\n");
        /*try {
            
            
            //ciclo di lettura invio al server e stampa risposta.
            //avvio del thread
            
        } catch (IOException e) {
        //in seguito ad ogni fallimento il socket deve essere chiusa, altrimenti
        //verrà chiusa dal metodo run() del thread
            try {
                socket.close();
            } catch(IOException e2){}
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public void writeToServer(String msg){
        out.println(msg);
        out.flush();
    }
}
