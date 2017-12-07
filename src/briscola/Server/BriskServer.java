/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.LogicApplicativa.MainBrain;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



/**
 *
 * @author x.ruan
 */
public class BriskServer extends Thread{
    /******* sockets *******/
    protected ServerSocket mainSocket;
    
    /********* cache **********/
    public ArrayList<User> usersConnected;
    public ArrayList<MainBrain> rooms;
    
    /************ CONSTRUCTOR *************/
    public BriskServer(int port){
        usersConnected = new ArrayList();
        rooms = new ArrayList<>();
        
        try{
            mainSocket = new ServerSocket(port);
            System.out.println("CentralServer: started");
            System.out.println("Server Socket: " + mainSocket);
            
        }catch(IOException ex){
            System.out.println("socket activation failed");
        }
    }
    
    /****************** OPERATORS ****************/
    @Override
    public void run(){
    
        try{
            while(true){
                System.out.println("server listening");
                Socket fromClientSocket = mainSocket.accept();
                
                //---- apply user connection
                User user = new User(this, new UserSocket(fromClientSocket));
                usersConnected.add(user);
                updateRooms();
                System.out.println("connection estabilished " + fromClientSocket);
            }
        }catch(IOException ex){
            System.out.println("Accept failed.");
           
            try{
                System.out.println("Multiserver: closing");
                mainSocket.close();
            }catch(Exception iex){}
            
            System.exit(1);
        }
    }
    
    public void disconnectUser(User user){
        usersConnected.remove(user);
        user.suicide();
    }
    
    public void broadcastMessage(String msg){
        for (User user : usersConnected){
            user.writeSocket(msg);
        }
    }
    
    public void updateRooms(){
        System.out.println("Aggiorno rooms");
        if(rooms.size() > 0){
            for (MainBrain room : rooms){
                System.out.println("Scrivo la room " + room.roomName);
                broadcastMessage("12.get." + room.roomName);
            }
        }
    }
    
    public MainBrain getRoomByName(String name){
        MainBrain targetRoom = null;
        for (MainBrain room : rooms){
            if (room.roomName.equals(name)) targetRoom = room;
        }
        return targetRoom;
    }
    public MainBrain createRoom(int nPlayers, String roomName){
        MainBrain room = null;
        try {
            room = new MainBrain(nPlayers, roomName);
            rooms.add(room);
            updateRooms();
        } catch (Exception ex) {}
        return room;
    }
    
    public void createRoom(int nPlayers, String roomName, User host){
        MainBrain room = null;
        try {
            room = new MainBrain(nPlayers, roomName);
            rooms.add(room);
            host.joinGame(room);
            System.out.println(host.getNickname() + " e entrato nella stanza " + roomName);
            updateRooms();
        } catch (Exception ex) {}
    }

    public String getRooms() {
        String dat_room = "";
        for (MainBrain mainbrain : rooms){
            dat_room += mainbrain.roomName + "-";
        }
        return dat_room;
    }
}
