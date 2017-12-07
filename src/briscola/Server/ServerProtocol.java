/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package centralbriscolaserver;



/**
 *
 * @author n.lo piccolo
 */
public class ServerProtocol {
    public static final String bootstrap = "01.";
    public static final String beginMatch = "03.";
    public static final String turno_giocatore = "02.";
    
    public static final String gameHeader = "04.";
    public static final String joinGame = "jon.";
    public static final String exitGame = "exg.";
    
    public static final String winRound = "05.";
    public static final String playCard = "";
    
    public static final String cardHeader = "06.";
    public static final String get_mano = "hnd.";
    public static final String get_card = "crd.";
    //NUOVO!!
    public static final String play_card = "ply.";
    
    public static final String messagechat = "09.";
    
    public static final String briscola = "11.";
    
    public static final String roomHeader = "12.";
//    public static final String enterRoom = "ent.";//12.ent.roomname
//    public static final String sync_room = "syn.";
//    public static final String get_room_name = "get.";
    public static final String create_room_2p = "cr2.";
    public static final String create_room_4p = "cr4.";
    public static final String remove_room = "rmv.";
    
    private User user;
    private String pacchetto = null;
    /************* CONSTRUCTOR ***************/
    public ServerProtocol (User _user) {
        user = _user;
    }

    /************* METODI GENERALI STATICI **************/
     public static String getHeader(String msg){
        return msg.substring(0, 3);
    }
     public static String getIdentifier(String msg){
        return msg.substring(3,7);
    }
     //prende il contenuto nel caso in cui ci sia un identifier
     public static String getContentId(String msg){
        return msg.substring(7);
    }
     //prende il contenuto nel caso in cui non ci sia un identifier
     public static String getContent(String msg){
        return msg.substring(3);
    }
    //metodo di spacchettamento
    public String route(String msg){
        
        String  header = getHeader(msg);
        System.out.println("Header: " + header);
        String identifier = null;
        
        switch(header) {
            /************** decode *************/
            case bootstrap: { receiveBootstrap(msg); break;}
            case gameHeader: {
                identifier = getIdentifier(msg);
                System.out.println("Identidier: " + identifier);
                switch(identifier) {
                    case exitGame: {sendExitGame(msg); break;}
                }
                break;
            }
            
            case winRound: { sendWonRound(msg); break;}
            
            case cardHeader: {
                break;
            }
            
//            case messagechat: { pacchetto = messageChat(msg); break;}

            case roomHeader: {
                identifier = getIdentifier(msg);
                System.out.println("Identidier: " + identifier);
                switch(identifier) {
                    case create_room_2p: {receiveRoom2p(msg);break;}
                    case create_room_4p: {receiveRoom4p(msg);break;}
                }
                break;
            }
            default: { System.out.println("ERROR: BAD HEADER"); break; }
        }
        return pacchetto;
    }
    
    //Questo metodo ritorna il giocatore che deve giocare la carta **
    public void sendTurnoGiocatore(String turno) {
        pacchetto = turno_giocatore + turno;
        System.out.println("Sto Inviando " + pacchetto);
        user.getGame().broadcastMessage(pacchetto);
    }
    
    //segnala ai client un nuovo utente entrato nella stanza **
    //codificare il nome come 001,002,003,004 per non avere problemi nel client
    public void sendJoinGame(String nro, String nick) {
         pacchetto = gameHeader + joinGame + nro + "." + nick;
        System.out.println("Sto Inviando " + pacchetto);
        user.getGame().broadcastMessage(pacchetto);
    }

    //segnala che un utente si è disconesso **
    public void sendExitGame(String utente) {
        pacchetto = gameHeader + exitGame + utente;
        System.out.println("Sto Inviando" + pacchetto);
        user.getGame().broadcastMessage(pacchetto);
    }
    
    //invia il numero del giocatore che ha vinto la mano **
    public void sendWonRound(String nro) {
        pacchetto = winRound + nro;
        System.out.println("Sto Inviando" + pacchetto);
        user.getGame().broadcastMessage(pacchetto);
    }
    
    //segnala la mano iniziale del giocatore **
    public void sendMano(String c1,String c2,String c3) {
        String pacchetto = cardHeader + get_mano + (c1 + "-" + c2 + "-" + c3);
        System.out.println("Sto Inviando" + pacchetto);
        user.writeSocket(pacchetto);
    }
    
    //segnala al server la carta giocata *?
    public void playedCard(String msg) {
        String g = null;
        String carta = null;
        String pos = null;//posizione della carta nella mano
        carta = getIdentifier(msg);
        g =  msg.substring(7,10);
        pos =  msg.substring(11,14);
        System.out.println(g + " gioca " + carta + " nella posizione " + pos);
        //metodo che decide cosa fare con tali info
    }
    public void sendCard(String carta) {
        pacchetto = cardHeader + get_card + carta;
        System.out.println("Sto Inviando" + pacchetto);
        user.writeSocket(pacchetto);
    }
    
    
    //DA IMPLEMENTARE
    public String messageChat(String msg) {
        return getIdentifier(msg);   
    }
    //*-
    public String sendBriscola(String msg) {
        return getIdentifier(msg);
    }
    
    public void receiveEnterRoom(String msg) {
       String roomName = getContent(msg);
       user.joinGame(roomName);
    }
    //**
    /*public void sendSyncRoom(String msg) {
        String pacchetto = roomHeader + sync_room + user.connectedServer.getRooms();
        user.connectedServer.broadcastMessage(pacchetto);
    }*/
    
    //**
    /*public void sendRoomName(String roomname) {
       String pacchetto = roomHeader + get_room_name + roomname;
       user.connectedServer.broadcastMessage(pacchetto);
    }*/
    
    //Rivece la richiesta di una nuova stanza da due e la crea **
    public void receiveRoom2p(String msg) {
        String ip = getContentId(msg);
        System.out.println("Nuovo room da due creata " + ip);
        user.connectedServer.createRoom(2, ip);
    }
    //Rivece la richiesta di una nuova stanza da quattro e la crea **
    public void receiveRoom4p(String msg) {
        String ip = getContentId(msg);
        System.out.println("Nuovo room da quattro creata: " + ip);
        user.connectedServer.createRoom(4, ip, user);
    }
    
    //Rimuove la stanza a tutti i client connessi **
    public void sendRemoveRoom(String room) {
        String pacchetto = roomHeader + remove_room + room;
        user.connectedServer.broadcastMessage(pacchetto);
    }
    
    //Riceve il saluto del nuovo client connesso **
    private void receiveBootstrap(String msg) {
        user.setNickname(getContent(msg)); 
        System.out.println(user.getNickname() + " e' entrato");
    }
}
