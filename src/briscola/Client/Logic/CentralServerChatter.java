/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.Logic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Gabriele
 */
public class CentralServerChatter extends Thread{
    
    private final Socket socket;
    
    private BufferedReader socket_reader;
    private InputStreamReader ir;
    private ClientProtocol protocol;
    
    public CentralServerChatter(Socket s, ClientThread client) throws IOException{
        protocol = new ClientProtocol(client);
        System.out.println("CSC\tCSC attivo");
        socket = s;
        socket_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }
    
    //il client rimane in ascolto
    @Override
    public void run(){
         while(true){
            try {
                Thread.sleep(10);
                String message = readFromSocket();
                System.out.println("CSC\tSto ricevendo " + message);
                decodeMessage(message);
            } catch (InterruptedException ex) {
            } catch (IOException ex) {
            }
         
        }
    }
    
    public String readFromSocket() throws IOException{
        return socket_reader.readLine();
    }
    
    public void decodeMessage(String msg) throws IOException{
        protocol.route(msg);
    }
    
    private void disconnect() throws IOException{
        socket.close();
    }  
}
