/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server.LogicApplicativa;

import briscola.Client.Logic.Carta;
import java.util.ArrayList;

/**
 *
 * @author Gabriele
 */
public class TwoPlayersABU {
    
    private ArrayList carte;
    private int nGiocatori;
    private int punti;
    private Carta cartaG1;
    private Carta cartaG2;
    
    public TwoPlayersABU(){
        this.nGiocatori = 2;
    }
    
    public int calcolaPunti(){
        punti = 0;
        Carta c;
        for(int i = 0; i < nGiocatori; i++){
            c = (Carta) carte.get(i);
            punti += c.getPunti();
        }
        return punti;
    }
    
    public String vincitoreRound(ArrayList cGiocate, String turno){
        carte = cGiocate;
        String winner = "";
        cartaG1 = (Carta) cGiocate.get(0);
        cartaG2 = (Carta) cGiocate.get(1);
        if(cartaG1.isBriscola() && !cartaG2.isBriscola()) winner = "g1";
        else if(!cartaG1.isBriscola() && cartaG2.isBriscola()) winner = "g2";
        else if(cartaG1.isBriscola() && cartaG2.isBriscola()){
            if(cartaG1.getValore() > cartaG2.getValore()) winner = "g1";
            else winner = "g2";
        }
        else if(!cartaG1.isBriscola() && !cartaG2.isBriscola()){
            if(cartaG1.getSeme().equals(cartaG2.getSeme())){
                if(cartaG1.getValore() > cartaG2.getValore()) winner = "g1";
                else winner = "g2";
            } else {
                if(turno.equals("g1")) winner = "g1";
                else winner = "g2";
            }
        }
        
        return winner;
    }
}
