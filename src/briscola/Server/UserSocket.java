/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package centralbriscolaserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import sun.security.ssl.Debug;

/**
 *
 * @author x.ruan
 */
public class UserSocket{
    /******* sockets in/out *******/
    public final Socket fromClientSocket;
    
    /********* components ********/
    //---- socket input
    private BufferedReader socket_reader;
    //---- socket output
    private PrintWriter socket_writer;
    
    /**************** CONSTRUCTOR
     * @param client_socket ********************/
    public UserSocket(Socket client_socket){
        fromClientSocket = client_socket;
        initialise_components();
    }
    
    /***************** OPERATORS ***********************/
    private void initialise_components() {
        try{
            //---- init input stream
           socket_reader = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));

            //---- init outstream
            socket_writer = new PrintWriter(fromClientSocket.getOutputStream());

            //---- init outstream
            socket_writer = new PrintWriter(fromClientSocket.getOutputStream());
            
            System.out.println("socket activated");
        }catch(IOException ex){
            Debug.println("socket activation:", "failed");
        }
    }
    
    
    
    /***************** METHODS ***************/
    public String readFromSocket() throws IOException{
        return socket_reader.readLine();
    }
    
    protected void writeSocket(String msg){
        socket_writer.println(msg);
        socket_writer.flush();
    }
    
    public void delete(){
        try{
            socket_reader.close();
            socket_writer.close();
            fromClientSocket.close();

        }catch(Exception ex){}
    }
}
