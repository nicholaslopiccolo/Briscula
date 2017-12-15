/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server;

import briscola.Server.LogicApplicativa.MainBrain;
import static briscola.Server.ServerProtocol.*;
import java.io.IOException;
import java.net.Socket;


/**
 *
 * @author besterranx
 */
public final class User extends Thread{
    /************* PROPERTIES ************/
    protected BriskServer connectedServer;
    private String nickname;
    public UserSocket connectedSocket;
    public Socket socket;
    private MainBrain ingame = null;
    private ServerProtocol decoder;
    
    
    /************* CONSTRUCTORS *************/
    public User(BriskServer server, Socket usercnt) throws IOException{
        this.socket = usercnt;
        connectedServer = server;
        connectedSocket = new UserSocket(socket);
        decoder = new ServerProtocol(this);
        System.out.println("USER\tNew User created.");
        start();
    }
    
    /************** OPERATORS ***************/
    
    private void decodeMessage(String msg){
        System.out.println("USER\tDecodifico " + decoder.getHeader(msg));
        decoder.route(msg);
    }
    
    
    /************** METHODS ****************/
    @Override
    public void run() {
        System.out.println("SERVER\tEntro nel run dell'user");
        try {
            while (true) {
                String message = connectedSocket.readFromSocket();
                System.out.println("USER\t" + message);
                decodeMessage(message);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            //resolve
            if (isInGame()) {
                leaveGame(ingame);
            }
//            connectedServer.disconnectUser(this);
            System.out.println("USER\t" + nickname + " si e disconnesso");
        }
    }
    
    public MainBrain getGame(){
        return ingame;
    }
    
    public void setGame(MainBrain game){
        ingame = game;
    }
    
    public ServerProtocol getDecoder(){
        return decoder;
    }
    
    public String getNickname(){
        return nickname;
    }
    
    public boolean isInGame(){
        if (ingame == null) return false;
        else return true;
    }
    
    public void joinGame(String addr) {
//        MainBrain game = connectedServer.getRoomByName(roomName);
//        if (game != null) {
//            try {
//                game.addUser(this);
//            } catch (Exception ex) {
//            }
//            ingame = game;
//        }
    }
    
    public void joinGame(MainBrain game){
        try{
            game.addUser(this);
        }catch(Exception ex){}
        ingame = game;
    }
    
    public void leaveGame(MainBrain game){
        game.removeUser(this);
        ingame = null;
    }
    
    public void writeSocket(String msg){
        connectedSocket.writeSocket(msg);
    }
    
    public void setNickname(String _name){
        nickname = _name;
    }

    public void suicide(){
        System.out.println(nickname + " e' morto");
        connectedSocket.delete();
        try{
            join();
        }catch(Exception ex){}
    }
    
    public UserSocket getSocket(){
        return connectedSocket;
    }
}
