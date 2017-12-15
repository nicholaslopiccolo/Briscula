/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server.LogicApplicativa;

//import logicaapplicativa.FourPlayersBrain;
import briscola.Client.Logic.Carta;
import briscola.Server.LogicApplicativa.FourPlayersBrain;
import briscola.Server.LogicApplicativa.Writer;
import briscola.Server.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainBrain {
    
    //private ArrayList carteGiocate;
    protected ArrayList mazzo;
    private String[] semi = {"d", "c", "s", "b"};
    protected Carta briscola;
    protected ArrayList<User> users;
    public static int nGiocatori;
    public static Writer game;
    public String roomName;
    public static TwoPlayersBrain TwoPBrain;
    public static FourPlayersBrain FourPBrain;
    
    public MainBrain(int ng, ArrayList<User> user) throws IOException{
        System.out.println("MAINBRAIN\tGame for " + ng + " started");
        mazzo = new ArrayList();
        users = new ArrayList();
        nGiocatori = ng;
        creaMazzo();
        for(User u : user){
            addUser(u);
        }
    }
 
    private void stampaMazzo(){
        for(int i = 0; i < 40; i++){
            Carta c = (Carta) mazzo.get(i);
            System.out.println(i + ": " + c.getNumero() + " di " + c.getSeme() + ". Briscola: " + c.isBriscola());
        }
    }
    
    private void creaMazzo() throws IOException{
        int n, i;
        for(n = 1; n < 11; n++){
            for(i = 0; i < semi.length; i++){
                    mazzo.add(new Carta(n, semi[i]));
            }
        }
        Collections.shuffle(mazzo);
    }
    
    public User getHost(){
        return users.get(0);
    }
    
    public void addUser(User user) throws IOException{
        users.add(user);
        user.setGame(this);
        if (users.size() == nGiocatori){
            if (nGiocatori == 2) {
                broadcastMessage(user.getDecoder().sendIsFull(2));
                TwoPBrain = new TwoPlayersBrain(mazzo, users);
            } else if (nGiocatori == 4) {
                
                user.getDecoder().sendIsFull(4);
                FourPBrain = new FourPlayersBrain(mazzo);
            }
            game = new Writer(users);
            for(int i = 0; i < nGiocatori; i++){
                //Reader r = new Reader(users.get(i));
            }
        }
    }
    
    public void removeUser(User user){
        getHost().getDecoder().sendExitGame(user.getNickname());
        if (user.equals(getHost())){
            destroy();
        }
        else {users.remove(user);}
    }
    //quando un creatore esce dalla propria stanza essa viene distutta
    private void destroy() {
//        getHost().getDecoder().sendRemoveRoom(this.roomName);
    }
    
    public void broadcastMessage(String pacchetto){
        for (User user : users){
            user.writeSocket(pacchetto);
        }
    }
}
