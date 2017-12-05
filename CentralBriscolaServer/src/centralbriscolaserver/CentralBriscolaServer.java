/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package centralbriscolaserver;

import java.io.IOException;
import server.BriskServer;

/**
 *
 * @author Gabriele
 */
public class CentralBriscolaServer {

    private static final int PORT = 4444;

    public static void main(String[] args) throws IOException {
        BriskServer server = new BriskServer(4444);
        server.start();
    }
}
