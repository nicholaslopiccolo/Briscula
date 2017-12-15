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
import java.util.ArrayList;
import java.util.Collections;
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
        public static final String endGame = "end.";
        public static final String restartGame = "rst.";
        public static final String finish = "fin.";
        public static final String punti = "scr.";
    
    public static final String winRound = "05.";
    public static final String playCard = "";
    
    public static final String cardHeader = "06.";
        public static final String get_mano = "hnd.";
        public static final String get_card = "crd.";
        public static final String play_card = "ply.";
    
    public static final String messagechat = "09.";
    
    public static final String briscola = "11.";
    
    public static final String roomHeader = "12.";
    public static final String enterRoom = "ent.";
    public static final String create_room_2p = "cr2.";
    public static final String create_room_4p = "cr4.";
    public static final String room_full = "fll.";
    
    
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
                    case exitGame: {user.suicide(); break;}
                    case restartGame: { restartGame(getContentId(msg)); break;}
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
    
    public String sendPunti(int puntiG1, int puntiG2){
        pacchetto = gameHeader + punti + puntiG1 + "." + puntiG2;
        System.out.println("SERVERPROTOCOL\tSto Inviando: " + pacchetto);
        return pacchetto;
    }
    
    public String sendEndGame(){
        pacchetto = gameHeader + endGame;
        System.out.println("SERVERPROTOCOL\tInvio: " + pacchetto);
        return pacchetto;
    }
    
    public String sendFinisci(){
        pacchetto = gameHeader + finish;
        System.out.println("SERVERPROTOCOL\tInvio: " + pacchetto);
        return pacchetto;
    }
    
    public String sendExitGame(){
        pacchetto = gameHeader + exitGame;
        System.out.println("SERVERPROTOCOL\tSto inviando: " + pacchetto);
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
    }
    
    public void receiveRoom4p(String msg) {
        String ip = getContentId(msg);
        System.out.println("SERVERPROTOCOL\tNuovo room da quattro creata: " + ip);
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
            MainBrain.FourPBrain.giocaCarta(player, stringToCarta(carta), position);
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
    
    private void restartGame(String players){
        int p = Integer.parseInt(players);
        if(p == 2){
            if(user.getGame().TwoPBrain.nGame < 2){
                ArrayList<Carta> mazzo = creaMazzo();
                user.getGame().TwoPBrain.nGame++;
                user.getGame().TwoPBrain.startNewGame(mazzo);
            }
            else{
                user.getGame().TwoPBrain.broadcastMessage(sendFinisci());
            }
        } else {
//            user.getGame().FourPBrain.nGame++;
        }
    }
    
    private ArrayList<Carta> creaMazzo(){
        ArrayList<Carta> mazzo = new ArrayList();
        String[] semi = {"d", "c", "s", "b"};
        mazzo.clear();
        int n, i;
        for(n = 1; n < 11; n++){
            for(i = 0; i < semi.length; i++){
                try {
                    mazzo.add(new Carta(n, semi[i]));
                } catch (IOException ex) {}
            }
        }
        Collections.shuffle(mazzo);
        return mazzo;
    }

 
    
}
