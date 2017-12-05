/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.GUI.JPanelLogin;
import GUI.Carta;
import java.io.IOException;



/**
 *
 * @author n.lo piccolo
 */

/*
Legenda:
- ** = fatto;
- *? = fato da testare;
- *- = da fare;
*/
public class ClientProtocol {
    
    private static final String bootstrap = "01.";
    
    private static final String turno_giocatore = "02.";
    
    private static final String gameHeader = "04.";
    private static final String joinGame = "jon.";
    private static final String exitGame = "exg.";
    
    private static final String winRound = "05.";
    
    private static final String cardHeader = "06.";
    private static final String get_mano = "hnd.";
    private static final String get_card = "crd.";
    
    private static final String play_card = "ply.";
    
    private static final String messagechat = "09.";
    private static final String briscola = "11.";
    
    private static final String roomHeader = "12.";
//    private static final String sync_room = "syn.";
    public static final String enterRoom = "ent.";//12.ent.roomname
//    private static final String get_room_name = "get.";
    private static final String create_room_2p = "cr2.";
    private static final String create_room_4p = "cr4.";
    private static final String remove_room = "rmv.";
    private String pacchetto = null;
    
    //stringhe giocatori
    private static String g1 = null;
    private static String g2 = null;
    private static String g3 = null;
    private static String g4 = null;
    
    //Stringa che tiene conto del turno attuale
    public static String turno = null;
    //Stringa che tiene conto della posizione dell'ultima carta giocata
    private String posizione_mancante = null;
    
    public ClientProtocol () {
    }
    
    //metodi generali
     public String getHeader(String msg){
        return msg.substring(0, 3);
    }
     public String getIdentifier(String msg){
        return msg.substring(3,7);
    }
     //prende il contenuto nel caso in cui ci sia un identifier
     public String getContentId(String msg){
        return msg.substring(7);
    }
     //prende il contenuto nel caso in cui non ci sia un identifier
     public String getContent(String msg){
        return msg.substring(3);
    }
    //metodo di spacchettamento *?
    public String route(String msg){
        
        String  header = getHeader(msg);
        String identifier = null;
        switch(header) {
            case turno_giocatore: { turnoGiocatore(msg); break;}
            case gameHeader: {
                identifier = getIdentifier(msg);
                
                switch(identifier) {
                    case joinGame: {joinGame(msg); break;}
                    case exitGame: {exitGame(msg); break;}
                }
                break;}
            
            case winRound: { wonRound(msg); break;}
            
            case cardHeader: {
                identifier = getIdentifier(msg);
                switch(identifier) {
                    case get_mano: {getMano(msg); break;}
//                    case get_card: { getCard(msg); break;}
                }
                break;}
            
            case messagechat: { pacchetto = messageChat(msg); break;}
            
            case briscola: { pacchetto = briscola(msg); break;}
            
            case roomHeader: {
                identifier = getIdentifier(msg);
                switch(identifier) {
//                    case sync_room: { pacchetto = syncRoom(msg);break;}
//                    case get_room_name: { UpdateRoomName(msg);break;}
                    case remove_room: { pacchetto = removeRoom(msg);break;}
                }
                break;}
            default: { System.out.println("ERROR: BAD HEADER"); break; }
        }
        return pacchetto;
    }
    //impacchettamento per invio al server
    
    //il pacchetto conterrà header+nome es. 01.pippo
    //solo invio al server **
    public String sendBootstrap(String nome) {
        pacchetto = bootstrap;
        pacchetto = pacchetto + nome;
        return pacchetto;
    }
    //Questo metodo ritorna il giocatore che deve giocare la carta *?
    public void turnoGiocatore(String msg) {
        turno = getContent(msg);
        //turno contiene il nro del giocatore che deve giocare la carta 
    }
    //ricezione nuovo utente entrato nella stanza SERVER *?
    public void joinGame(String msg) {
        String nro = null;
        String nick = null;
        nro = msg.substring(7,10); //ne sono poco sicuro da verificare
        nick = msg.substring(12); //ne sono poco sicuro da verificare
        switch (nro) {
            case "001": { g1 = nick; break; }
            case "002": { g2 = nick; break; }
            case "003": { g3 = nick; break; }
            case "004": { g4 = nick; break; }
        }
    }
    //**
    public void exitGame(String msg) {
        String exit = null;
        exit = getContentId(msg);
        System.out.println(exit + " é uscito");
    }
    //*?
    public void wonRound(String msg) {
        String g = null;
        g = getContentId(msg);//giocatore che ha vinto la mano
        System.out.println(g + " Vince la mano");
        //metodo che decide cosa fare con tali info (calcolo del punteggio)
    }
    //*-
    public void getMano(String msg) {
        //String pacchetto = cardHeader + get_mano + (c1 + "-" + c2 + "-" + c3);
        String c1,c2,c3;
        c1 = msg.substring(7,10); 
        c2 = msg.substring(11,14);
        c3 = msg.substring(15,18);
        System.out.println(c1 + " " + c2 + " " + c3);
        //metodo che inserisce le carte nella mano
    }
    //*?
    public void getCard(String msg) throws IOException {
        int nro = 0;
        String seme = null;
        nro = Integer.parseInt(msg.substring(7,9));
        seme = msg.substring(9,10);
        System.out.println(nro + " " + seme);
//        Carta c = new Carta(nro, seme);
    }
    //*-
    public String messageChat(String msg) {
        pacchetto = getIdentifier(msg);
        return pacchetto;    
    }
    //*-
    public String briscola(String msg) {
        pacchetto = getIdentifier(msg);
        return pacchetto;   
    }
     //non lo modifico ma è da fare
    public String playCard(String carta, int position){
        //posizione.carta
        posizione_mancante = Integer.toString(position);
        return cardHeader + play_card + Integer.toString(position)+"."+ carta; 
    }
    //*-
    public String syncRoom(String msg) {
        pacchetto = getIdentifier(msg);
        return pacchetto;    
    }
    //*-
    public void UpdateRoomName(String roomName) {
        String room;
        room = getContentId(roomName);
        JPanelLogin.updateRooms(room);
    }
    //*-
    public String createRoom2p(String ip) {
        pacchetto = roomHeader + create_room_2p + ip;
        System.out.println("Sto inviando: " + pacchetto);
        return pacchetto;
    }
    //*-
    public String createRoom4p(String ip) {
        pacchetto = roomHeader + create_room_4p + ip;
        System.out.println("Sto inviando: " + pacchetto);
        return pacchetto;
    }
    //*-
    public String removeRoom(String msg) {
        pacchetto = getIdentifier(msg);
        return pacchetto;
    }
    
}
