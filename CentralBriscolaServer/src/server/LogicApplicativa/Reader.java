
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.LogicApplicativa;

import centralbriscolaserver.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriele
 */
public class Reader extends Thread{
    
    private BufferedReader reader;
    private User user;
    
    public Reader(User u) throws IOException{
        user = u;
        reader = new BufferedReader(new InputStreamReader(user.connectedSocket.fromClientSocket.getInputStream()));
        start();
    }
    
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(10);
                String message = reader.readLine();
                System.out.println(message);
                decodeMessage(message);
            } catch (InterruptedException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void decodeMessage(String msg){}
    
}
