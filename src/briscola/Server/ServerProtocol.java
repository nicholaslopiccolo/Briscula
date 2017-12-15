/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server;

import briscola.Client.Logic.Carta;
import briscola.Server.LogicApplicativa.MainBrain;
import briscola.Server.LogicApplicativa.TwoPlayersBrain;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



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
        public static final String player = "usr.";
        public static final String dontDrawMazzo = "maz.";
        public static final String dontDrawBriscola = "brk.";
    
    public static final String winRound = "05.";
    public static final String playCard = "";
    
    public static final String cardHeader = "06.";
        public static final String get_mano = "hnd.";
        public static final String get_card = "crd.";
        public static final String play_card = "ply.";
    
    public static final String messagechat = "09.";
    
    public static final String briscola = "11.";
    
    public static final String roomHeader = "12.";
    public static final String enterRoom = "ent.";//12.ent.roomname
//    public static final String sync_room = "syn.";
//    public static final String get_room_name = "get.";
    public static final String create_room_2p = "cr2.";
    public static final String create_room_4p = "cr4.";
    public static final String room_full = "fll.";
    public static final String remove_room = "rmv.";
    
    
    private User user;
    private String pacchetto = null;
    
    public ServerProtocol (User _user) {
        user = _user;
    }

    
    public static String getHeader(String msg){
        return msg.substring(0, 3);
    }
    public static String getIdentifier(String msg){
        return msg.substring(3,7);
    }
    public static String getContentId(String msg){
        return msg.substring(7);
    }
    public static String getContent(String msg){
        return msg.substring(3);
    }
     
    public String route(String msg){
        
        String  header = getHeader(msg);
        System.out.println("SERVERPROTOCOL\tHeader: " + header);
        String identifier = null;
        
        switch(header) {
            /************** decode *************/
            case bootstrap: { receiveBootstrap(msg); break;}
            case gameHeader: {
                identifier = getIdentifier(msg);
                System.out.println("SERVERPROTOCOL\tIdentidier: " + identifier);
                switch(identifier) {
                    case exitGame: {sendExitGame(msg); break;}
                }
                break;
            }
            case winRound: { sendWonRound(msg); break;}
            case cardHeader: {
                identifier = getIdentifier(msg);
                switch(identifier){
                    case play_card: {
                        impostaCartaGiocata(getContentId(msg));
                        break;
                    }
                }
                break;
            }
//            case messagechat: { pacchetto = messageChat(msg); break;}
            case roomHeader: {
                identifier = getIdentifier(msg);
                System.out.println("SERVERPROTOCOL\tIdentidier: " + identifier);
                switch(identifier) {
                    case create_room_2p: {receiveRoom2p(msg);break;}
                    case create_room_4p: {receiveRoom4p(msg);break;}
                    case enterRoom: {receiveEnterRoom(msg); break;}
                }
                break;
            }
            
            case messagechat:{
                broadcastChat(msg);
                break;
            }
            default: { System.out.println("SERVERPROTOCOL\tERROR: BAD HEADER"); break; }
        }
        return pacchetto;
    }
    
    
   //MANDA AL CLIENT
    public String sendIsFull (int n){
        pacchetto = roomHeader + room_full + n;
        System.out.println("SERVERPROTOCOL\tSto Inviando " + pacchetto);
        //user.getGame().getHost().connectedServer.broadcastMessage(pacchetto);
        return pacchetto;
    }
    
    public void sendCard(String carta) {
        pacchetto = cardHeader + get_card + carta;
        System.out.println("SERVERPROTOCOL\tSto Inviando" + pacchetto);
        user.writeSocket(pacchetto);
    }
    
    public void sendMano(String c1,String c2,String c3) {
        String pacchetto = cardHeader + get_mano + (c1 + "-" + c2 + "-" + c3);
        System.out.println("SERVERPROTOCOL\tSto Inviando" + pacchetto);
        user.writeSocket(pacchetto);
    }
    
    public void sendWonRound(String nro) {
        pacchetto = winRound + nro;
        System.out.println("SERVERPROTOCOL\tSto Inviando" + pacchetto);
        user.getGame().broadcastMessage(pacchetto);
    }
    
    public void sendExitGame(String utente) {
        pacchetto = gameHeader + exitGame + utente;
        System.out.println("SERVERPROTOCOL\tSto Inviando" + pacchetto);
        user.getGame().broadcastMessage(pacchetto);
    }
    
    public void sendChiSono(String player){
        pacchetto = gameHeader + this.player + player;
        System.out.println("SERVERPROTOCOL\tSto Inviando" + pacchetto);
        user.writeSocket(pacchetto);
    }
    
    public String sendPlayedCard(String player, String carta, int position){
        pacchetto = cardHeader + play_card + player + "." + carta + "." + position;
        System.out.println("SERVERPROTOCOL\tMando: " + pacchetto);
        return pacchetto;
    }
    
    public String sendWinnerRound(String winner){
        pacchetto = winRound + winner;
        System.out.println("SERVERPROTOCOL\tMando: " + pacchetto);
        return pacchetto;
    }
    
    public String sendDontDrawMazzo(){
        pacchetto = gameHeader + dontDrawMazzo;
        System.out.println("SERVERPROTOCOL\tInvio: " + pacchetto);
        return pacchetto;
    }
    
    public String sendDontDrawBriscola(){
        pacchetto = gameHeader + dontDrawBriscola;
        System.out.println("SERVERPROTOCOL\tInvio: " + pacchetto);
        return pacchetto;
    }
    
    
    //DA IMPLEMENTARE
    public String messageChat(String msg) {
        return getIdentifier(msg);   
    }
    
    public void receiveEnterRoom(String msg) {
       String roomName = getContent(msg);
       user.joinGame(roomName);
    }
    
        //segnala al server la carta giocata *?
    public void playedCard(String msg) {
        String g = null;
        String carta = null;
        String pos = null;//posizione della carta nella mano
        carta = getIdentifier(msg);
        g =  msg.substring(7,10);
        pos =  msg.substring(11,14);
        System.out.println("SERVERPROTOCOL\t"+g + " gioca " + carta + " nella posizione " + pos);
        //metodo che decide cosa fare con tali info
    }

    
    //RICEVE DA CLIENT
    public void receiveRoom2p(String msg) {
        String ip = getContentId(msg);
        System.out.println("SERVERPROTOCOL\tNuovo room da due creata " + ip);
//        user.connectedServer.createRoom(2, user);
    }
    
    public void receiveRoom4p(String msg) {
        String ip = getContentId(msg);
        System.out.println("SERVERPROTOCOL\tNuovo room da quattro creata: " + ip);
//        user.connectedServer.createRoom(4, ip, user);
    }
    
    private void receiveBootstrap(String msg) {
        user.setNickname(getContent(msg)); 
        System.out.println("SERVERPROTOCOL\t"+user.getNickname() + " e' entrato");
    }

    private void impostaCartaGiocata(String msg) {
        String player = msg.substring(0,2);
        String carta = msg.substring(3,6);
        int position = Integer.parseInt(msg.substring(7));
        Carta c1 = stringToCarta(carta);
        System.out.println("SERVERPROTOCOL\tCarta giocata: " + c1.getNumero() + " di " + c1.getSeme() + " dalla posizione " + position);
        if(MainBrain.nGiocatori == 2){
            MainBrain.TwoPBrain.giocaCarta(player, stringToCarta(carta), position);
        } else if(MainBrain.nGiocatori == 4){
//            MainBrain.FourPBrain.carteGiocate.add(stringToCarta(carta));
        }
    }
    
    private Carta stringToCarta(String c){
        int numero = Integer.valueOf(c.substring(0,2));
        String seme = c.substring(2);
        try {
            Carta carta = new Carta(numero, seme);
            return carta;
        } catch (IOException ex) {}
        return null;
    }

    private void broadcastChat(String msg) {
        if (user == null) System.out.println("user null");
        String message = messagechat+getContent(msg);
        System.out.println("Server sending message:"+message);
        user.connectedServer.broadcastMessage(messagechat+user.getNickname()+":"+getContent(msg));
    }
}
