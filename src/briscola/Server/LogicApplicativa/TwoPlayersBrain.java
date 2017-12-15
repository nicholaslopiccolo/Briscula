/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server.LogicApplicativa;


import briscola.Client.Logic.Carta;
import briscola.Server.ServerProtocol;
import briscola.Server.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author g.evangelista
 */
public class TwoPlayersBrain extends Thread{
    
    public static ArrayList<Carta> carteGiocate;
    private ArrayList<Carta> mazzo;
    private ArrayList<Carta> carteG1;
    private ArrayList<Carta> carteG2;
    private ArrayList<User> players;
    private int puntiG1;
    private int puntiG2;
    private int cartaDaPescare = 0;
    private static String turno = "g1";
    private TwoPlayersABU abu;
    private Carta briscola;
    private ServerProtocol protocol;
    public int nGame = 0;
  
    
    public TwoPlayersBrain(ArrayList mazzo, ArrayList<User> users) throws IOException{
        this.players = users;
        carteGiocate = new ArrayList();
        carteG1 = new ArrayList();
        carteG2 = new ArrayList();
        abu = new TwoPlayersABU();
        protocol = new ServerProtocol(users.get(0));
        restartGame(mazzo);
    }
    
    private void calcolaBriscola() {
        briscola = mazzo.remove(6);
        mazzo.add(mazzo.size(), briscola);
    }
    
    private void calcolaBriscole(){
        for(int i = 0; i < mazzo.size(); i++){
            Carta c = (Carta) mazzo.get(i);
            if(c.getSeme().equals(briscola.getSeme())){
                c.setBriscola(true);
                mazzo.remove(i);
                mazzo.add(i, c);
            }
        }
    }
    
    private void daiCarte(){
        if(mazzo.size() > 0)
            if(cartaDaPescare == 0){
                for(int i = 0; i < 6; i++){
                    Carta c = (Carta) mazzo.remove(0);
                    if(i%2 == 0){
                        carteG1.add(c);
                    }
                    else{
                        carteG2.add(c);
                    }
                    cartaDaPescare++;
                }
                sendMani(carteG1, carteG2);
            }
            else
                for(int i = 0; i < 2; i++){
                    Carta c = (Carta) mazzo.remove(0);
                    if(i%2 == 0){
                        carteG1.add(c);
                        String n = cartaToString(c);
                        players.get(0).getDecoder().sendCard(n);
                        if(mazzo.size() == 1){
                            broadcastMessage(protocol.sendDontDrawMazzo());
                        }
                        if(mazzo.size() == 0){
                            broadcastMessage(protocol.sendDontDrawBriscola());
                        }
                    }
                    else{
                        carteG2.add(c);
                        String n = cartaToString(c);
                        players.get(1).getDecoder().sendCard(n);
                        if(mazzo.size() == 1){
                            broadcastMessage(protocol.sendDontDrawMazzo());
                        }
                        if(mazzo.size() == 0){
                            broadcastMessage(protocol.sendDontDrawBriscola());
                        }
                    }
                    cartaDaPescare++;
                }
    }
    
    private void sendMani(ArrayList<Carta> carteG1, ArrayList<Carta> carteG2){
        String carta11 = cartaToString(carteG1.get(0));
        String carta12 = cartaToString(carteG1.get(1));
        String carta13 = cartaToString(carteG1.get(2));
        String carta21 = cartaToString(carteG2.get(0));
        String carta22 = cartaToString(carteG2.get(1));
        String carta23 = cartaToString(carteG2.get(2));
        players.get(0).getDecoder().sendMano(carta11, carta12, carta13);
        players.get(1).getDecoder().sendMano(carta21, carta22, carta23);
    }
    
    private String calcolaVincitore(String carta1, String carta2){
        Carta cartaTMP1 = null;
        Carta cartaTMP2 = null;
        try {
            cartaTMP1 = new Carta(Integer.parseInt(carta1.substring(0,2)), carta1.substring(2));
            cartaTMP2 = new Carta(Integer.parseInt(carta2.substring(0,2)), carta2.substring(2));
            for(int j = 0; j < carteG1.size(); j++){
                Carta carta = carteG1.get(j);
                if (cartaTMP1.getNumero() == carta.getNumero() && cartaTMP1.getSeme().equals(carta.getSeme()))
                    cartaTMP1 = carteG1.remove(j);
            }
            for(int j = 0; j < carteG2.size(); j++){
                Carta carta = carteG2.get(j);
                if (cartaTMP2.getNumero() == carta.getNumero() && cartaTMP2.getSeme().equals(carta.getSeme()))
                    cartaTMP2 = carteG2.remove(j);                
            }
            
            carteGiocate.add(cartaTMP1);
            carteGiocate.add(cartaTMP2);
            
            String winner = abu.vincitoreRound(carteGiocate, turno);
            
            if(winner.equals("g1")){ 
                puntiG1 += abu.calcolaPunti();
                System.out.println("\nVince G1!");
                System.out.println("G1: " + puntiG1 + "\tG2: " + puntiG2);
                System.out.println("\n");
                turno = "g1";
            } else { 
                puntiG2 += abu.calcolaPunti();
                System.out.println("\nVince G2!");
                System.out.println("G1: " + puntiG1 + "\tG2: " + puntiG2);
                System.out.println("\n");
                turno = "g2";
            }
        } catch (IOException ex) {}
        return turno;
    }
    
    public void giocaCarta(String player, Carta c, int position){
        System.out.println("2P BRAIN\tCARTA SALVATA");
        if(player.equals("g1"))
            carteGiocate.add(0, c);
        else
            carteGiocate.add(carteGiocate.size(), c);
        broadcastMessage(protocol.sendPlayedCard(player, cartaToString(c), position));
        if(carteGiocate.size()<2){
            if(turno.equals("g1"))
                turno = "g2";
            else
                turno = "g1";
            sendTurno();
        } else {
            if(turno.equals("g1"))
                turno = "g2";
            else
                turno = "g1";
            Carta c1 = carteGiocate.remove(0);
            Carta c2 = carteGiocate.remove(0);
            String winner = calcolaVincitore(cartaToString(c1), cartaToString(c2));
            turno = winner;
            broadcastMessage(protocol.sendWinnerRound(winner));
            carteGiocate.clear();
            sendTurno();
            broadcastMessage(protocol.sendPunti(puntiG1, puntiG2));
            if(mazzo.size() > 0)
                daiCarte();
            else
                broadcastMessage(protocol.sendEndGame());
        }
    }
    
    private String cartaToString(Carta c){
        //calcolo la stringa della carta
        int numero = c.getNumero();
        String n = "";
        if(numero < 10){
            n = "0" + numero + c.getSmallSeme();
        } else {
            n = numero + c.getSmallSeme();
        }
        return n;
    }

    private void sendTurno(){
        System.out.println("2P Brain\tTurno: " + turno);
        broadcastMessage(protocol.turno_giocatore + turno);
    }
    
    public void broadcastMessage(String pacchetto){
        for (User user : players){
            user.writeSocket(pacchetto);
        }
    }
    
    public void restartGame(ArrayList<Carta> m){
        this.mazzo = m;
        carteG1.clear();
        carteG2.clear();
        cartaDaPescare = 0;
        turno = "g1";
        calcolaBriscola();
        calcolaBriscole();
        //MANDO CHI È CHI
        if(nGame%2 == 0){
            players.get(0).getDecoder().sendChiSono("g1");
            players.get(1).getDecoder().sendChiSono("g2");
        } else {
            players.get(1).getDecoder().sendChiSono("g1");
            players.get(0).getDecoder().sendChiSono("g2");
        }
        //MANDO TURNO
        sendTurno();
        //MANDA BRISCOLA
        System.out.println("2P BRAIN\tMando briscola");
        broadcastMessage(protocol.briscola + cartaToString(briscola));
        carteGiocate.clear();
        daiCarte();
    }
}
