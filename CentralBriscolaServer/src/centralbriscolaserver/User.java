/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package centralbriscolaserver;

import server.LogicApplicativa.MainBrain;
import java.io.IOException;
import server.BriskServer;
import static centralbriscolaserver.ServerProtocol.*;
/**
 *
 * @author besterranx
 */
public final class User extends Thread{
    /************* PROPERTIES ************/
    protected BriskServer connectedServer;
    private String nickname;
    public UserSocket connectedSocket;
    private MainBrain ingame = null;
    private ServerProtocol decoder;
    
    /************* CONSTRUCTORS *************/
    public User(BriskServer server, UserSocket userSocket, String _nickname) throws IOException{
        connectedSocket = userSocket;
        connectedServer = server;
        decoder = new ServerProtocol(this);
        setName(_nickname);
        
        start();
        connectedServer.updateRooms();
    }
    
    public User(BriskServer server, UserSocket usercnt) throws IOException{
        connectedSocket = usercnt;
        connectedServer = server;
        decoder = new ServerProtocol(this);
        start();
        connectedServer.updateRooms();
    }
    
    /************** OPERATORS ***************/
    private void decodeMessage(String msg) throws IOException{
        System.out.println("Decodifico " + decoder.getHeader(msg));
        System.out.println(decoder.route(msg));
    }
    
    /************** METHODS ****************/
    @Override
    public void run() {
        try {
            while (true) {
                String message = connectedSocket.readFromSocket();
                System.out.println(message);
                decodeMessage(message);
            }
        } catch (Exception ex) {
            //resolve
            if (isInGame()) {
                leaveGame(ingame);
            }
            connectedServer.disconnectUser(this);
            System.out.println(nickname + " si e disconnesso");
        }
    }
    
    public String readPlayer_Card(){
        while(true){
           try{
               String pacchetto = connectedSocket.readFromSocket();
               if (getHeader(pacchetto).equals(cardHeader) && 
                   getIdentifier(pacchetto).equals(play_card)){
                   System.out.println(pacchetto);
                   return pacchetto;
               }
           }catch(Exception ex){}
        }
    }
    
    public MainBrain getGame(){
        return ingame;
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
    
    public void joinGame(String roomName) {
        MainBrain game = connectedServer.getRoomByName(roomName);
        if (game != null) {
            try {
                game.addUser(this);
            } catch (Exception ex) {
            }
            ingame = game;
        }
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
