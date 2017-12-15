package briscola.Server.LogicApplicativa;

import briscola.Client.Logic.Carta;
import java.io.IOException;
import java.util.ArrayList;





/**
 *
 * @author g.evangelista
 */
public class FourPlayersBrain extends Thread{
    
    private ArrayList carteGiocate;
    private ArrayList mazzo;
    private ArrayList carteG1;
    private ArrayList carteG2;
    private ArrayList carteG3;
    private ArrayList carteG4;
    private int puntiSquadra1;
    private int puntiSquadra2;
    private int cartaDaPescare = 0;
    private String turno = "g1";
    private FourPlayersABU abu;
    private Carta briscola;

    
    
    
    public FourPlayersBrain(ArrayList mazzo) throws IOException{
        this.mazzo = mazzo;
        carteGiocate = new ArrayList();
        carteG1 = new ArrayList();
        carteG2 = new ArrayList();
        carteG3 = new ArrayList();
        carteG4 = new ArrayList();
        abu = new FourPlayersABU();
        trovaBriscola();
        calcolaBriscole();
        gioca();
    }
    
    private void stampaMazzo(){
        for(int i = 0; i < 40; i++){
            Carta c = (Carta) mazzo.get(i);
            System.out.println(i+1 + ": " + c.getNumero() + " di " + c.getSeme() + ". Briscola: " + c.isBriscola());
        }
    }
    
    private void trovaBriscola(){
        Carta cBriscola  = (Carta) mazzo.remove(6);
        mazzo.add(mazzo.size(), cBriscola);
        briscola = cBriscola;
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
        stampaMazzo();
        while(cartaDaPescare < 39){
            System.out.println("Briscola: " + briscola.getNumero() + " di " + briscola.getSeme() + "\n");
            carteGiocate.clear();
            daiCarte();
            eseguiTurno();
        }
        for(int i = 0; i < 2; i++){
            carteGiocate.clear();
            eseguiTurno();
        }
        System.out.println("\n\n\nFINITO\n\n\n");
        if(puntiSquadra1 > puntiSquadra2){ 
            System.out.println("Squadra 1 VINCE");
            return "g1";
        } else {
            System.out.println("Squadra 2 VINCE");
            return "g2";
        }
        
    }
    
    private void eseguiTurno() throws IOException{
        System.out.println("Carte pescate: " + cartaDaPescare + "/40\n\n");
        stampaCarte();
            
        String s1 = MainBrain.game.gioca();
        String s2 = MainBrain.game.gioca();
        String s3 = MainBrain.game.gioca();
        String s4 = MainBrain.game.gioca();


        Carta cartaTMP1 = null;
        Carta cartaTMP2 = null;
        Carta cartaTMP3 = null;
        Carta cartaTMP4 = null;

        if(turno.equals("g1")){
            cartaTMP1 = new Carta(Integer.parseInt(s1.substring(2,4)), s1.substring(4,5));
            cartaTMP2 = new Carta(Integer.parseInt(s2.substring(2,4)), s2.substring(4,5));
            cartaTMP3 = new Carta(Integer.parseInt(s3.substring(2,4)), s3.substring(4,5));
            cartaTMP4 = new Carta(Integer.parseInt(s4.substring(2,4)), s4.substring(4,5));
        } else if (turno.equals("g2")){
            cartaTMP2 = new Carta(Integer.parseInt(s1.substring(2,4)), s1.substring(4,5));
            cartaTMP3 = new Carta(Integer.parseInt(s2.substring(2,4)), s2.substring(4,5));
            cartaTMP4 = new Carta(Integer.parseInt(s3.substring(2,4)), s3.substring(4,5));
            cartaTMP1 = new Carta(Integer.parseInt(s4.substring(2,4)), s4.substring(4,5));
        } else if (turno.equals("g3")){
            cartaTMP3 = new Carta(Integer.parseInt(s1.substring(2,4)), s1.substring(4,5));
            cartaTMP4 = new Carta(Integer.parseInt(s2.substring(2,4)), s2.substring(4,5));
            cartaTMP1 = new Carta(Integer.parseInt(s3.substring(2,4)), s3.substring(4,5));
            cartaTMP2 = new Carta(Integer.parseInt(s4.substring(2,4)), s4.substring(4,5));
        } else if (turno.equals("g4")){
            cartaTMP4 = new Carta(Integer.parseInt(s1.substring(2,4)), s1.substring(4,5));
            cartaTMP1 = new Carta(Integer.parseInt(s2.substring(2,4)), s2.substring(4,5));
            cartaTMP2 = new Carta(Integer.parseInt(s3.substring(2,4)), s3.substring(4,5));
            cartaTMP3 = new Carta(Integer.parseInt(s4.substring(2,4)), s4.substring(4,5));
        }

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
        for(int j = 0; j < carteG3.size(); j++){
            Carta carta = (Carta) carteG3.get(j);
            if (cartaTMP3.getNumero() == carta.getNumero() && cartaTMP3.getSeme().equals(carta.getSeme()))
                cartaTMP3 = (Carta) carteG3.remove(j);                
        }
        for(int j = 0; j < carteG4.size(); j++){
            Carta carta = (Carta) carteG4.get(j);
            if (cartaTMP4.getNumero() == carta.getNumero() && cartaTMP4.getSeme().equals(carta.getSeme()))
                cartaTMP4 = (Carta) carteG4.remove(j);                
        }
        

        carteGiocate.add(cartaTMP1);
        carteGiocate.add(cartaTMP2);
        carteGiocate.add(cartaTMP3);
        carteGiocate.add(cartaTMP4);

        String winner = "g1";
        winner = abu.vincitoreRound(carteGiocate, turno);

        if(winner.equals("g1")){ 
            puntiSquadra1 += abu.calcolaPunti();
            System.out.println("\nVince G1!");
            System.out.println("Squadra 1: " + puntiSquadra1 + "\tSquadra 2: " + puntiSquadra2);
            System.out.println("\n");
            turno = "g1";
        } else if(winner.equals("g2")) { 
            puntiSquadra2 += abu.calcolaPunti();
            System.out.println("\nVince G2!");
            System.out.println("Squadra 1: " + puntiSquadra1 + "\tSquadra 2: " + puntiSquadra2);
            System.out.println("\n");
            turno = "g2";
        } else if(winner.equals("g3")) { 
            puntiSquadra1 += abu.calcolaPunti();
            System.out.println("\nVince G3!");
            System.out.println("Squadra 1: " + puntiSquadra1 + "\tSquadra 2: " + puntiSquadra2);
            System.out.println("\n");
            turno = "g3";
        } else if(winner.equals("g4")) { 
            puntiSquadra2 += abu.calcolaPunti();
            System.out.println("\nVince G4!");
            System.out.println("Squadra 1: " + puntiSquadra1 + "\tSquadra 2: " + puntiSquadra2);
            System.out.println("\n");
            turno = "g4";
        }
    }
    
    private void daiCarte(){
        int nCarta = 1;
        if(mazzo.size() > 0){
            if(cartaDaPescare == 0)
                for(int i = 0; i < 12; i++){
                    if(nCarta > 4) nCarta = 1;
                    dai(nCarta);
                    nCarta++;
                }
            else
                for(int i = 0; i < 4; i++){
                    if(nCarta > 4) nCarta = 1;
                    dai(nCarta);
                    nCarta++;
                }
        }
    }
    
    private void stampaCarte(){
        if (turno.equals("g1")){
            stampaCarteG1();
            stampaCarteG2();
            stampaCarteG3();
            stampaCarteG4();
        } else if (turno.equals("g2")){
            stampaCarteG2();
            stampaCarteG3();
            stampaCarteG4();
            stampaCarteG1();
        } else if (turno.equals("g3")){
            stampaCarteG3();
            stampaCarteG4();
            stampaCarteG1();
            stampaCarteG2();
        } else {
            stampaCarteG4();
            stampaCarteG1();
            stampaCarteG2();
            stampaCarteG3();
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
    
    private void stampaCarteG3(){
        System.out.println("G3:");
        for(int i = 0; i < carteG3.size(); i++){
            Carta c = (Carta) carteG3.get(i);
            System.out.print(c.getNumero() + " di " + c.getSeme() + " / ");
        }
        System.out.println("");
    }
    
    private void stampaCarteG4(){
        System.out.println("G4:");
        for(int i = 0; i < carteG4.size(); i++){
            Carta c = (Carta) carteG4.get(i);
            System.out.print(c.getNumero() + " di " + c.getSeme() + " / ");
        }
        System.out.println("");
    }
    
    private void dai(int nCarta){
        Carta c = (Carta) mazzo.remove(0);
        if(turno.equals("g1")){
            if(nCarta == 1){
                carteG1.add(c);
            } else if(nCarta == 2){
                carteG2.add(c);
            } else if(nCarta == 3){
                carteG3.add(c);
            } else if(nCarta == 4){
                carteG4.add(c);
            }
            cartaDaPescare++;
        } else if(turno.equals("g2")){
            if(nCarta == 1){
                carteG2.add(c);
            } else if(nCarta == 2){
                carteG3.add(c);
            } else if(nCarta == 3){
                carteG4.add(c);
            } else if(nCarta == 4){
                carteG1.add(c);
            }
            cartaDaPescare++;
        } else if(turno.equals("g3")){
            if(nCarta == 1){
                carteG3.add(c);
            } else if(nCarta == 2){
                carteG4.add(c);
            } else if(nCarta == 3){
                carteG1.add(c);
            } else if(nCarta == 4){
                carteG2.add(c);
            }
            cartaDaPescare++;
        } else if(turno.equals("g4")){
            if(nCarta == 1){
                carteG4.add(c);
            } else if(nCarta == 2){
                carteG1.add(c);
            } else if(nCarta == 3){
                carteG2.add(c);
            } else if(nCarta == 4){
                carteG3.add(c);
            }
            cartaDaPescare++;
        }
    }
    
}
