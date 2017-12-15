/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Server.LogicApplicativa;

import briscola.Client.Logic.Carta;
import briscola.Server.LogicApplicativa.FourPlayersBrain;
import briscola.Server.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainBrain {
    
    //private ArrayList carteGiocate;
    protected ArrayList mazzo;
    private String[] semi = {"d", "c", "s", "b"};
    protected Carta briscola;
    public ArrayList<User> users;
    public static int nGiocatori;
    public String roomName;
    public static TwoPlayersBrain TwoPBrain;
    public static FourPlayersBrain FourPBrain;
    public int repeatedGame = 0;
    
    public MainBrain(int ng, ArrayList<User> user) throws IOException{
        System.out.println("MAINBRAIN\tGame for " + ng + " started");
        mazzo = new ArrayList();
        users = new ArrayList();
        nGiocatori = ng;
        creaMazzo();
        for(User u : user){
            u.joinGame(this);
            addUser(u);
        }
    }
    
    public ArrayList<Carta> creaMazzo() throws IOException{
        mazzo = new ArrayList();
        mazzo.clear();
        int n, i;
        for(n = 1; n < 11; n++){
            for(i = 0; i < semi.length; i++){
                    mazzo.add(new Carta(n, semi[i]));
            }
        }
        Collections.shuffle(mazzo);
        return mazzo;
    }
    
    public User getHost(){
        return users.get(0);
    }
    
    public void addUser(User user) throws IOException{
        users.add(user);
        if (users.size() == nGiocatori){
            if (nGiocatori == 2) {
                broadcastMessage(user.getDecoder().sendIsFull(2));
                TwoPBrain = new TwoPlayersBrain(mazzo, users);
            } else if (nGiocatori == 4) {
                broadcastMessage(user.getDecoder().sendIsFull(4));
                FourPBrain = new FourPlayersBrain(mazzo, users);
            }
        }
    }
    
    public void removeUser(User user){
        broadcastMessage(getHost().getDecoder().sendExitGame());
        users.remove(user);
    }
    
    public void broadcastMessage(String pacchetto){
        for (User user : users){
            if(user != null)
                user.writeSocket(pacchetto);
        }
    }
}
