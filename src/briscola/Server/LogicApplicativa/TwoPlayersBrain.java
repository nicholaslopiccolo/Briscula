/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.LogicApplicativa;

import centralbriscolaserver.ServerProtocol;
import server.LogicApplicativa.Carta;
import server.LogicApplicativa.TwoPlayersABU;
import centralbriscolaserver.User;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author g.evangelista
 */
public class TwoPlayersBrain extends Thread{
    
    private ArrayList carteGiocate;
    private ArrayList mazzo;
    private ArrayList<Carta> carteG1;
    private ArrayList<Carta> carteG2;
    private ArrayList<User> players;
    private int puntiG1;
    private int puntiG2;
    private int cartaDaPescare = 0;
    private String turno = "g1";
    private TwoPlayersABU abu;
    private Carta briscola;
    private ServerProtocol protocol;
  
    
    public TwoPlayersBrain(ArrayList mazzo, ArrayList<User> users) throws IOException{
        this.mazzo = mazzo;
        this.players = users;
        carteGiocate = new ArrayList();
        carteG1 = new ArrayList();
        carteG2 = new ArrayList();
        abu = new TwoPlayersABU();
        calcolaBriscole();
        protocol = new ServerProtocol(players.get(0));
        //MANDO TURNO
        protocol.sendTurnoGiocatore(turno);
        gioca();
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
    
    public String gioca() throws IOException{
        while(cartaDaPescare < 39){
            carteGiocate.clear();
            daiCarte();
            eseguiTurno();
        }
        for(int i = 0; i < 2; i++){
            carteGiocate.clear();
            eseguiTurno();
        }
        System.out.println("\n\n\nFINITO\n\n\n");
        if(puntiG1 > puntiG2){ 
            System.out.println("Giocatore 1 VINCE");
            return "g1";
        } else {
            System.out.println("Giocatore2 VINCE");
            return "g2";
        }
        
    }
    
    private void eseguiTurno() throws IOException{
        System.out.println("LA BRISCOLA E' " + briscola.getNumero() + " DI " + briscola.getSeme().toUpperCase() + "\n");
        
        System.out.println("Carte pescate: " + cartaDaPescare + "/40\n\n");
        
        String c1 = "";
        String c2 = "";
        
        if (turno.equals("g1")){
            stampaCarteG1();
            stampaCarteG2();
            c1 = players.get(0).readPlayer_Card();
            turno = "g2";
            protocol.sendTurnoGiocatore(turno);
            c2 = players.get(1).readPlayer_Card();
        } else {
            stampaCarteG2();
            stampaCarteG1();
            c2 = players.get(1).readPlayer_Card();
            turno = "g1";
            protocol.sendTurnoGiocatore(turno);
            c1 = players.get(0).readPlayer_Card();
        }
        
        Carta cartaTMP1 = new Carta(Integer.parseInt(c1.substring(2,4)), c1.substring(4,5));
        Carta cartaTMP2 = new Carta(Integer.parseInt(c2.substring(2,4)), c2.substring(4,5));

        for(int j = 0; j < carteG1.size(); j++){
            Carta carta = (Carta) carteG1.get(j);
            if (cartaTMP1.getNumero() == carta.getNumero() && cartaTMP1.getSeme().equals(carta.getSeme()))
                cartaTMP1 = (Carta) carteG1.remove(j);
        }
        for(int j = 0; j < carteG2.size(); j++){
            Carta carta = (Carta) carteG2.get(j);
            if (cartaTMP2.getNumero() == carta.getNumero() && cartaTMP2.getSeme().equals(carta.getSeme()))
                cartaTMP2 = (Carta) carteG2.remove(j);                
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
        
        protocol.sendTurnoGiocatore(turno);
    }
    
    private void daiCarte(){
        if(mazzo.size() > 0)
            if(cartaDaPescare == 0)
                for(int i = 0; i < 6; i++){
                    Carta c = (Carta) mazzo.remove(0);
                    if(i%2 == 0){
                        carteG1.add(c);
                        //calcolo posizione dellla carta nella mano
                        String n = calcolaCarta(c, i);
                        //la mando
                        players.get(0).getDecoder().sendCard(n);
                    }
                    else{
                        carteG2.add(c);
                        //calcolo la stringa della carta
                        String n = calcolaCarta(c, i);
                        //la mando
                        players.get(1).getDecoder().sendCard(n);
                    }
                    cartaDaPescare++;
                }
            else
                for(int i = 0; i < 2; i++){
                    Carta c = (Carta) mazzo.remove(0);
                    if(i%2 == 0){
                        carteG1.add(c);
                        String n = calcolaCarta(c, i);
                        players.get(0).getDecoder().sendCard(n);
                    }
                    else{
                        carteG2.add(c);
                        String n = calcolaCarta(c, i);
                        players.get(1).getDecoder().sendCard(n);
                    }
                    cartaDaPescare++;
                }
    }
    
    private void stampaCarteG1(){
        System.out.println("G1:");
        for(int i = 0; i < carteG1.size(); i++){
            Carta c = (Carta) carteG1.get(i);
            System.out.print(c.getNumero() + " di " + c.getSeme() + " / ");
        }
        System.out.println("");
    }
    
    private void stampaCarteG2(){
        System.out.println("G2:");
        for(int i = 0; i < carteG2.size(); i++){
            Carta c = (Carta) carteG2.get(i);
            System.out.print(c.getNumero() + " di " + c.getSeme() + " / ");
        }
        System.out.println("");
    }
    
    private String calcolaCarta(Carta c, int i){
    //calcolo la stringa della carta
        int numero = c.getNumero();
        String n = "";
        if(numero < 10){
            n = "0" + numero + c.getSeme();
        } else {
            n = numero + c.getSeme();
        }
        return n;
    }
    
    private void giocaG1() throws IOException{
        /*String c = "";
        if(turno%2 == 0) c = "g1";
        else c = "g2";
        System.out.print("Gioca" + ":  ");
        c += myInput.readLine();
        turno++;*/
//        return carta;
    }
}
