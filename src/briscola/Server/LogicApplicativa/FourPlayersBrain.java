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
public class FourPlayersBrain extends Thread{
    
    private Carta[] carteGiocate;
    private ArrayList<Carta> mazzo;
    private ArrayList<Carta> carteG1;
    private ArrayList<Carta> carteG2;
    private ArrayList<Carta> carteG3;
    private ArrayList<Carta> carteG4;
    private ArrayList<User> players;
    private int puntiSquadra1;
    private int puntiSquadra2;
    private int cartaDaPescare = 0;
    private String turno = "g1";
    private FourPlayersABU abu;
    private Carta briscola;
    public int nGame = 0;
    private ServerProtocol protocol;

    
    
    
    public FourPlayersBrain(ArrayList mazzo, ArrayList<User> users) 
            throws IOException{
        this.mazzo = mazzo;
        this.players = users;
        carteGiocate = new Carta[4];
        carteG1 = new ArrayList();
        carteG2 = new ArrayList();
        carteG3 = new ArrayList();
        carteG4 = new ArrayList();
        abu = new FourPlayersABU();
        protocol = new ServerProtocol(users.get(0));
        startNewGame(mazzo);
    }
    
    private void stampaMazzo(){
        for(int i = 0; i < 40; i++){
            Carta c = (Carta) mazzo.get(i);
            System.out.println(i+1 + ": " + c.getNumero() + " di " + c.getSeme() + ". Briscola: " + c.isBriscola());
        }
    }
    
    private void calcolaBriscola(){
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
    
    private String calcolaVincitore(String s1, String s2, String s3, String s4) 
            throws IOException{
        //Istanzio delle carte temporanee di ogni carta giocata dal giocatore
        Carta cartaTMP1 = new Carta(Integer.parseInt(s1.substring(0,2)), s1.substring(2));
        Carta cartaTMP2 = new Carta(Integer.parseInt(s2.substring(0,2)), s2.substring(2));
        Carta cartaTMP3 = new Carta(Integer.parseInt(s3.substring(0,2)), s3.substring(2));
        Carta cartaTMP4 = new Carta(Integer.parseInt(s4.substring(0,2)), s4.substring(2)); 

        //rimuovo dalle carte dei ruspettivi giocatori le carte giocate
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
        
        //Le aggiungo alle carte giocate
        carteGiocate[0] = cartaTMP1;
        carteGiocate[1] = cartaTMP2;
        carteGiocate[2] = cartaTMP3;
        carteGiocate[3] = cartaTMP4;
        
        
        //calcolo il vincitore
        String winner = abu.vincitoreRound(carteGiocate, turno);

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
        return turno;
    }
    
    private void daiCarte(){
        int nCarta = 1;
        if(mazzo.size() > 0){
            if(cartaDaPescare == 0){
                for(int i = 0; i < 12; i++){
                    if(nCarta > 4) nCarta = 1;
                    dai(nCarta);
                    nCarta++;
                }
                sendMani(carteG1, carteG2, carteG3, carteG4);
            }
            else
                for(int i = 0; i < 4; i++){
                    if(nCarta > 4) nCarta = 1;
                    dai(nCarta, true);
                    nCarta++;
                }
        }
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
        if(mazzo.size() == 1){
            broadcastMessage(protocol.sendDontDrawMazzo());
        }
        if(mazzo.size() == 0){
            broadcastMessage(protocol.sendDontDrawBriscola());
        }
    }
    
    private void dai(int nCarta, boolean bool){
        Carta c = (Carta) mazzo.remove(0);
        if(turno.equals("g1")){
            if(nCarta == 1){
                sendCartaG1(cartaToString(c), c);
            } else if(nCarta == 2){
                sendCartaG2(cartaToString(c), c);
            } else if(nCarta == 3){
                sendCartaG3(cartaToString(c), c);
            } else if(nCarta == 4){
                sendCartaG4(cartaToString(c), c);
            }
            cartaDaPescare++;
        } else if(turno.equals("g2")){
            if(nCarta == 1){
                sendCartaG2(cartaToString(c), c);
            } else if(nCarta == 2){
                sendCartaG3(cartaToString(c), c);
            } else if(nCarta == 3){
                sendCartaG4(cartaToString(c), c);
            } else if(nCarta == 4){
                sendCartaG1(cartaToString(c), c);
            }
            cartaDaPescare++;
        } else if(turno.equals("g3")){
            if(nCarta == 1){
                sendCartaG3(cartaToString(c), c);
            } else if(nCarta == 2){
                sendCartaG4(cartaToString(c), c);
            } else if(nCarta == 3){
                sendCartaG1(cartaToString(c), c);
            } else if(nCarta == 4){
                sendCartaG2(cartaToString(c), c);
            }
            cartaDaPescare++;
        } else if(turno.equals("g4")){
            if(nCarta == 1){
                sendCartaG4(cartaToString(c), c);
            } else if(nCarta == 2){
                sendCartaG1(cartaToString(c), c);
            } else if(nCarta == 3){
                sendCartaG2(cartaToString(c), c);
            } else if(nCarta == 4){
                sendCartaG3(cartaToString(c), c);
            }
            cartaDaPescare++;
        }
    }
    
    private void sendCartaG1(String carta, Carta c){
        carteG1.add(c);
        players.get(0).getDecoder().sendCard(carta);
    }
    
    private void sendCartaG2(String carta, Carta c){
        carteG2.add(c);
        players.get(1).getDecoder().sendCard(carta);
    }
    
    private void sendCartaG3(String carta, Carta c){
        carteG3.add(c);
        players.get(2).getDecoder().sendCard(carta);
    }
    
    private void sendCartaG4(String carta, Carta c){
        carteG4.add(c);
        players.get(3).getDecoder().sendCard(carta);
    }
    
    private void sendTurno(){
        System.out.println("4P Brain\tTurno: " + turno);
        broadcastMessage(protocol.turno_giocatore + turno);
    }
    
    public void broadcastMessage(String pacchetto){
        for (User user : players){
            user.writeSocket(pacchetto);
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

    private void startNewGame(ArrayList<Carta> m) {
        this.mazzo = m;
        carteG1.clear();
        carteG2.clear();
        carteG3.clear();
        carteG4.clear();
        cartaDaPescare = 0;
        turno = "g1";
        calcolaBriscola();
        calcolaBriscole();
        //MANDO TURNO
        sendTurno();
        //MANDO CHI È CHI
        players.get(0).getDecoder().sendChiSono("g1");
        players.get(1).getDecoder().sendChiSono("g2");
        players.get(2).getDecoder().sendChiSono("g3");
        players.get(3).getDecoder().sendChiSono("g4");
        //MANDA BRISCOLA
        broadcastMessage(protocol.briscola + cartaToString(briscola));
        daiCarte();
    }
    
    public void giocaCarta(String player, Carta c, int position){
        System.out.println("4P BRAIN\tCARTA SALVATA");
        if(player.equals("g1")) carteGiocate[0] = c;
        else if(player.equals("g2")) carteGiocate[1] = c;
        else if(player.equals("g3")) carteGiocate[2] = c;
        else if(player.equals("g4")) carteGiocate[3] = c;
        broadcastMessage(protocol.sendPlayedCard(player, cartaToString(c), position));
        if(!carteGiocateIsFull()){
            cambiaTurno();
            sendTurno();
        } else {
            cambiaTurno();
            Carta c1 = carteGiocate[0];
            Carta c2 = carteGiocate[1];
            Carta c3 = carteGiocate[2];
            Carta c4 = carteGiocate[3];
            String winner = "";
            try {
                winner = calcolaVincitore(cartaToString(c1), cartaToString(c2),
                        cartaToString(c3), cartaToString(c4));
            } catch (IOException ex) {}
            turno = winner;
            broadcastMessage(protocol.sendWinnerRound(winner));
            sendTurno();
            broadcastMessage(protocol.sendPunti(puntiSquadra1, puntiSquadra2));
            clearCarteGiocate();
            if(mazzo.size() > 0)
                daiCarte();
            else
                broadcastMessage(protocol.sendEndGame());
        }
    }
    
    private boolean carteGiocateIsFull(){
        if(carteGiocate[0] == null || carteGiocate[1] == null || 
                carteGiocate[2] == null || carteGiocate[3] == null)
            return false;
        else
            return true;
    }
    
    private void clearCarteGiocate(){
        carteGiocate[0] = null;
        carteGiocate[1] = null;
        carteGiocate[2] = null;
        carteGiocate[3] = null;
    }
    
    private void cambiaTurno(){
        if(turno.equals("g1")) turno = "g2";
        else if (turno.equals("g2")) turno = "g3";
        else if (turno.equals("g3")) turno = "g4";
        else if (turno.equals("g4")) turno = "g1";
    }

    private void sendMani(ArrayList<Carta> carteG1, ArrayList<Carta> carteG2, 
            ArrayList<Carta> carteG3, ArrayList<Carta> carteG4) {
        String carta11 = cartaToString(carteG1.get(0));
        String carta12 = cartaToString(carteG1.get(1));
        String carta13 = cartaToString(carteG1.get(2));
        String carta21 = cartaToString(carteG2.get(0));
        String carta22 = cartaToString(carteG2.get(1));
        String carta23 = cartaToString(carteG2.get(2));
        String carta31 = cartaToString(carteG3.get(0));
        String carta32 = cartaToString(carteG3.get(1));
        String carta33 = cartaToString(carteG3.get(2));
        String carta41 = cartaToString(carteG4.get(0));
        String carta42 = cartaToString(carteG4.get(1));
        String carta43 = cartaToString(carteG4.get(2));
        players.get(0).getDecoder().sendMano(carta11, carta12, carta13);
        players.get(1).getDecoder().sendMano(carta21, carta22, carta23);
        players.get(2).getDecoder().sendMano(carta31, carta32, carta33);
        players.get(3).getDecoder().sendMano(carta41, carta42, carta43);
    }
    
}
