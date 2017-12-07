/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.LogicApplicativa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Gabriele
 */
public class asd {
    
    static Carta briscola;
    static String[] semi = {"d", "c", "s", "b"};
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ArrayList mazzo;
        mazzo = new ArrayList();
        int n, i;
        for(n = 1; n < 11; n++){
            for(i = 0; i < 4; i++){
                    mazzo.add(new Carta(n, semi[i]));
            }
        }
        
        Collections.shuffle(mazzo);
        Carta cBriscola  = (Carta) mazzo.remove(6);
        mazzo.add(mazzo.size(), cBriscola);
        briscola = cBriscola;
        FourPlayersBrain fpb = new FourPlayersBrain(mazzo);
    }
}
