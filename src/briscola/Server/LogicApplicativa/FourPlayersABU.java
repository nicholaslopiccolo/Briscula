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
public class FourPlayersABU {
    
    private Carta[] carte;
    private int punti;
    private Carta cartaG1;
    private Carta cartaG2;
    private Carta cartaG3;
    private Carta cartaG4;
    private String winner;
    
    public FourPlayersABU(){
    }
    
    public int calcolaPunti(){
        punti = 0;
        for(int i = 0; i < 4; i++){
            cartaG1 = carte[i];
            punti += cartaG1.getPunti();
        }
        return punti;
    }
    
    public String vincitoreRound(Carta[] cGiocate, String turno){
        carte = cGiocate;
        cartaG1 = cGiocate[0];
        cartaG2 = cGiocate[1];
        cartaG3 = cGiocate[2];
        cartaG4 = cGiocate[3]; 
      
        //g1 = si, g2 = no, g3 = no, g4 = no
        if(cartaG1.isBriscola() && !cartaG2.isBriscola() && !cartaG3.isBriscola() && !cartaG4.isBriscola()){
            winner = "g1";
            System.out.println("\nVince G1 perché è l'unica briscola\n");
        } 
        //g1 = no, g2 = si, g3 = no, g4 = no
        if(!cartaG1.isBriscola() && cartaG2.isBriscola() && !cartaG3.isBriscola() && !cartaG4.isBriscola()){
            winner = "g2";
            System.out.println("\nVince G2 perché è l'unica briscola\n");
        } 
        //g1 = no, g2 = no, g3 = si, g4 = no
        else if(!cartaG1.isBriscola() && cartaG3.isBriscola() && !cartaG2.isBriscola() && !cartaG4.isBriscola()){
            winner = "g3";
            System.out.println("\nVince G3 perché è l'unica briscola\n");
        }
        //g1 = no, g2 = no, g3 = no, g4 = si
        else if(!cartaG1.isBriscola() && !cartaG3.isBriscola() && !cartaG2.isBriscola() && cartaG4.isBriscola()){
            winner = "g4";
            System.out.println("\nVince G4 perché è l'unica briscola\n");
        }
        //g1 = si, g2 = si, g3 = no, g4 = no
        else if (cartaG1.isBriscola() && cartaG2.isBriscola() && !cartaG3.isBriscola() && !cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG2.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 su G2 perché è la briscola più alta\n");
            }
            else{
                winner = "g2";
                System.out.println("\nVince G2 su G1 perché è la briscola più alta\n");
            }
        }
        //g1 = si, g2 = no, g3 = no, g4 = si
        else if (cartaG1.isBriscola() && !cartaG2.isBriscola() && !cartaG3.isBriscola()  && cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG4.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 su G4 perché è la briscola più alta\n");
            }
            else{
                winner = "g4";
                System.out.println("\nVince G4 su G1 perché è la briscola più alta\n");
            }
        }
        //g1 = no, g2 = si, g3 = si, g4 = no
        else if(!cartaG1.isBriscola() && cartaG2.isBriscola() && cartaG3.isBriscola() && !cartaG4.isBriscola()){
            if(cartaG2.getValore() > cartaG3.getValore()){
                winner = "g2";
                System.out.println("\nVince G2 su G3 perché è la briscola più alta\n");
            }
            else{
                winner = "g3";
                System.out.println("\nVince G3 su G2 perché è la briscola più alta\n");
            }
        }
        //g1 = no, g2 = no, g3 = si, g4 = si
        else if(!cartaG1.isBriscola() && !cartaG2.isBriscola() && cartaG3.isBriscola() && cartaG4.isBriscola()){
            if(cartaG3.getValore() > cartaG4.getValore()){
                winner = "g3";
                System.out.println("\nVince G3 su G4 perché è la briscola più alta\n");
            }
            else{
                winner = "g4";
                System.out.println("\nVince G4 su G3 perché è la briscola più alta\n");
            }
        }
        //g1 = no, g2 = si, g3 = no, g4 = si
        else if(!cartaG1.isBriscola() && cartaG2.isBriscola() && !cartaG3.isBriscola() && cartaG4.isBriscola()){
            if(cartaG2.getValore() > cartaG4.getValore()){ 
                winner = "g2";
                System.out.println("\nVince G2 su G4 perché è la briscola più alta\n");
            }
            else {
                winner = "g4";
                System.out.println("\nVince G4 su G2 perché è la briscola più alta\n");
            }
        }
        //g1 = si, g2 = no, g3 = si, g4 = no
        else if(cartaG1.isBriscola() && !cartaG2.isBriscola() && cartaG3.isBriscola() && !cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG3.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 su G3 perché è la briscola più alta\n");
            }
            else winner = "g3";
            System.out.println("\nVince G3 su G1 perché è la briscola più alta\n");
        }
        //g1 = si, g2 = si, g3 = si, g4 = no
        else if(cartaG1.isBriscola() && cartaG2.isBriscola() && cartaG3.isBriscola() && !cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG2.getValore() && cartaG1.getValore() > cartaG3.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 su G2 e G3 perché è la briscola più alta\n");
            } else if(cartaG2.getValore() > cartaG1.getValore() && cartaG2.getValore() > cartaG3.getValore()){
                winner = "g2";
                System.out.println("\nVince G2 su G1 e G3 perché è la briscola più alta\n");
            } else {
                winner = "g3";
                System.out.println("\nVince G3 su G1 e G2 perché è la briscola più alta\n");
            }
        }
        //g1 = si, g2 = no, g3 = si, g4 = si
        else if(cartaG1.isBriscola() && !cartaG2.isBriscola() && cartaG3.isBriscola() && cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG3.getValore() && cartaG1.getValore() > cartaG4.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 su G3 e G4 perché è la briscola più alta\n");
            } else if(cartaG3.getValore() > cartaG1.getValore() && cartaG3.getValore() > cartaG4.getValore()){
                winner = "g3";
                System.out.println("\nVince G3 su G1 e G4 perché è la briscola più alta\n");
            } else {
                winner = "g4";
                System.out.println("\nVince G4 su G1 e G3 perché è la briscola più alta\n");
            }
        }
        //g1 = no, g2 = si, g3 = si, g4 = si
        else if(!cartaG1.isBriscola() && cartaG2.isBriscola() && cartaG3.isBriscola() && cartaG4.isBriscola()){
            if(cartaG2.getValore() > cartaG3.getValore() && cartaG2.getValore() > cartaG4.getValore()){
                winner = "g2";
                System.out.println("\nVince G2 su G3 e G4 perché è la briscola più alta\n");
            } else if(cartaG3.getValore() > cartaG2.getValore() && cartaG3.getValore() > cartaG4.getValore()){
                winner = "g3";
                System.out.println("\nVince G3 su G2 e G4 perché è la briscola più alta\n");
            } else {
                winner = "g4";
                System.out.println("\nVince G4 su G2 e G3 perché è la briscola più alta\n");
            }
        }
        //g1 = si, g2 = si, g3 = no, g4 = si
        else if(cartaG1.isBriscola() && cartaG2.isBriscola() && !cartaG3.isBriscola() && cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG2.getValore() && cartaG1.getValore() > cartaG4.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 su G2 e G4 perché è la briscola più alta\n");
            } else if(cartaG2.getValore() > cartaG1.getValore() && cartaG2.getValore() > cartaG4.getValore()){
                winner = "g2";
                System.out.println("\nVince G2 su G1 e G4 perché è la briscola più alta\n");
            } else {
                winner = "g4";
                System.out.println("\nVince G4 su G1 e G2 perché è la briscola più alta\n");
            }
        }
        //se sono tutte briscole
        else if(cartaG1.isBriscola() && cartaG3.isBriscola() && cartaG2.isBriscola() && cartaG4.isBriscola()){
            if(cartaG1.getValore() > cartaG2.getValore() && cartaG1.getValore() > cartaG3.getValore()
                    && cartaG1.getValore() > cartaG4.getValore()){
                winner = "g1";
                System.out.println("\nVince G1 perché è la briscola piu' alta\n");
            }
            else if(cartaG3.getValore() > cartaG1.getValore() && cartaG3.getValore() > cartaG2.getValore()
                    && cartaG3.getValore() > cartaG4.getValore()){
                winner = "g3";
                System.out.println("\nVince G3 perché è la briscola piu' alta\n");
            }
            else if(cartaG2.getValore() > cartaG1.getValore() && cartaG2.getValore() > cartaG3.getValore()
                    && cartaG2.getValore() > cartaG4.getValore()){
                winner = "g2";
                System.out.println("\nVince G2 perché è la briscola piu' alta\n");
            }
            else if(cartaG4.getValore() > cartaG1.getValore() && cartaG4.getValore() > cartaG2.getValore()
                    && cartaG4.getValore() > cartaG3.getValore()){
                winner = "g4";
                System.out.println("\nVince G4 perché è la briscola piu' alta\n");
            }
        }
        //se nessuna è briscola
        else if(!cartaG1.isBriscola() && !cartaG3.isBriscola() && !cartaG2.isBriscola() && !cartaG4.isBriscola()){
            if(turno.equals("g1")){
                winner = "g1";
                checkG2(turno, cartaG1);
            } else if(turno.equals("g2")){
                winner = "g2";
                checkG3(turno, cartaG2);
            } else if(turno.equals("g3")){
                winner = "g3";
                checkG4(turno, cartaG3);
            } else if(turno.equals("g4")){
                winner = "g4";
                checkG1(turno, cartaG4);
            }
        }
        
        return winner;
    }
    
    private void checkG1(String turno, Carta c) {
        if(turno.equals("g4")){
            if(cartaG1.getSeme().equals(cartaG4.getSeme()) && cartaG1.getValore() > cartaG4.getValore()){
                winner = "g1";
            }
            checkG2(turno, c);
            checkG3(turno, c);
        } else {
            if(cartaG1.getSeme().equals(c.getSeme()) && cartaG1.getValore() > c.getValore())
                winner = "g1";
        }
    }
    
    private void checkG2(String turno, Carta c) {
        if(turno.equals("g1")){
            if(cartaG2.getSeme().equals(cartaG1.getSeme()) && cartaG2.getValore() > cartaG1.getValore()){
                winner = "g2";
            }
            checkG3(turno, c);checkG4(turno, c);
        } else {
            if(cartaG2.getSeme().equals(c.getSeme()) && cartaG2.getValore() > c.getValore())
                winner = "g2";
        }
    }
    
    private void checkG3(String turno, Carta c) {
        if(turno.equals("g2")){
            if(cartaG3.getSeme().equals(cartaG2.getSeme()) && cartaG3.getValore() > cartaG2.getValore()){
                winner = "g3";
            }
            checkG4(turno, c);
            checkG1(turno, c);
        } else {
            if(cartaG3.getSeme().equals(c.getSeme()) && cartaG3.getValore() > c.getValore())
                winner = "g3";
        }
    }
    
    private void checkG4(String turno, Carta c) {
        if(turno.equals("g3")){
            if(cartaG4.getSeme().equals(cartaG4.getSeme()) && cartaG4.getValore() > cartaG4.getValore()){
                winner = "g4";
            }
            checkG1(turno, c);
            checkG2(turno, c);
        } else {
            if(cartaG4.getSeme().equals(c.getSeme()) && cartaG4.getValore() > c.getValore())
                winner = "g4";
        }
    }
}
