/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package briscola.Client.Logic;

import briscola.Main;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFrame;

/**
 *
 * @author n.lo piccolo
 */

/*
 Legenda:
 - ** = fatto;
 - *? = fato da testare;
 - *- = da fare;
 */
public class ClientProtocol {

    private static final String bootstrap = "01.";

    private static final String turno_giocatore = "02.";

    public static final String gameHeader = "04.";
    public static final String joinGame = "jon.";
    public static final String exitGame = "exg.";
    public static final String player = "usr.";
    public static final String dontDrawMazzo = "maz.";
    public static final String dontDrawBriscola = "brk.";
    public static final String endGame = "end.";
    public static final String restartGame = "rst.";
    public static final String finish = "fin.";
    public static final String punti = "scr.";

    public static final String winRound = "05.";

    public static final String cardHeader = "06.";
    public static final String get_mano = "hnd.";
    public static final String get_card = "crd.";
    public static final String play_card = "ply.";
    
    public static final String cardBg = "07.";

    public static final String messagechat = "09.";
    public static final String briscola = "11.";

    public static final String roomHeader = "12.";
    public static final String enterRoom = "ent.";
    public static final String create_room_2p = "cr2.";
    public static final String create_room_4p = "cr4.";
    public static final String room_full = "fll.";
    public static final String remove_room = "rmv.";
    public String pacchetto = null;

    //stringhe giocatori
    public static String g1 = null;
    public static String g2 = null;
    public static String g3 = null;
    public static String g4 = null;

    //Stringa che tiene conto del turno attuale
    public static String turno = null;
    //Stringa che tiene conto della posizione dell'ultima carta giocata
    public int posizione_mancanteg1 = 0;
    public int posizione_mancanteg2 = 0;

    public ClientThread clientThread;

    public ClientProtocol(ClientThread client) {
        this.clientThread = client;
    }

    //METODI
    public String getHeader(String msg) {
        return msg.substring(0, 3);
    }

    public String getIdentifier(String msg) {
        return msg.substring(3, 7);
    }

    public String getContentId(String msg) {
        return msg.substring(7);
    }

    public String getContent(String msg) {
        return msg.substring(3);
    }

    public String route(String msg) throws IOException {
        String header = getHeader(msg);
        String identifier = null;
        switch (header) {
            case turno_giocatore: {
                turnoGiocatore(msg);
                break;
            }
            case gameHeader: {
                identifier = getIdentifier(msg);

                switch (identifier) {
                    case joinGame: {
                        joinGame(msg);
                        break;
                    }
                    case exitGame: {
                        exitGame(msg);
                        break;
                    }
                    case player: {
                        setPlayer(getContentId(msg));
                        break;
                    }
                    case dontDrawMazzo: {
                        cancellaMazzo();
                        break;
                    }
                    case dontDrawBriscola: {
                        cancellaBriscola();
                        break;
                    }
                    case endGame: {
                        finisciPartita();
                        break;
                    }
                    case cardBg: {
                        reciveCardBg(msg);
                        break;
                    }
                    case punti: {
                        disegnaPunti(getContentId(msg));
                        break;
                    }
                    case finish: {
                        System.exit(0);
                        break;
                    }
                }
                break;
            }

            case briscola: {
                setBriscola(getContent(msg));
                break;
            }

            case winRound: {
                animateWonRound(getContent(msg));
                break;
            }

            case cardHeader: {
                identifier = getIdentifier(msg);
                switch (identifier) {
                    case get_card: {
                        try {
                            animatePescare(getContentId(msg));
                        } catch (InterruptedException ex) {
                        }
                        break;
                    }
                    case get_mano: {
                        setMano(getContentId(msg));
                        break;
                    }
                    case play_card: {
                        animateCard(getContentId(msg));
                        break;
                    }
                }
                break;
            }

            case messagechat: {
                pacchetto = messageChat(msg);
                break;
            }

            case roomHeader: {
                identifier = getIdentifier(msg);
                switch (identifier) {
                    case remove_room: {
                        pacchetto = removeRoom(msg);
                        break;
                    }
                    case room_full: {
                        roomIsFull(msg);
                        break;
                    }

                }
                break;
            }
            default: {
                System.out.println("ERROR: BAD HEADER");
                break;
            }
        }
        return pacchetto;
    }

    public void joinGame(String msg) {
        String nro = null;
        String nick = null;
        nro = msg.substring(7, 10); //da verificare
        nick = msg.substring(12); //da verificare
        switch (nro) {
            case "001": {
                g1 = nick;
                break;
            }
            case "002": {
                g2 = nick;
                break;
            }
            case "003": {
                g3 = nick;
                break;
            }
            case "004": {
                g4 = nick;
                break;
            }
        }
    }

    public void exitGame(String msg) {
        String exit = null;
        exit = getContentId(msg);
        System.out.println("CLIENTPROTOCOL\t" + exit + " é uscito");
    }

    public void getMano(String msg) {
        //String pacchetto = cardHeader + get_mano + (c1 + "-" + c2 + "-" + c3);
        String c1, c2, c3;
        c1 = msg.substring(7, 10);
        c2 = msg.substring(11, 14);
        c3 = msg.substring(15, 18);
        System.out.println("CLIENTPROTOCOL\t" + c1 + " " + c2 + " " + c3);
        //metodo che inserisce le carte nella mano
    }

    public String messageChat(String msg) {
        pacchetto = getIdentifier(msg);
        return pacchetto;
    }

    //MANDA AL SERVER
    public String createRoom2p(String ip) {
        pacchetto = roomHeader + create_room_2p + ip;
        System.out.println("CLIENTPROTOCOL\tSto inviando: " + pacchetto);
        return pacchetto;
    }

    public String createRoom4p(String ip) {
        pacchetto = roomHeader + create_room_4p + ip;
        System.out.println("CLIENTPROTOCOL\tSto inviando: " + pacchetto);
        return pacchetto;
    }

    public String enterRoom(String nomeUser) {
        pacchetto = roomHeader + enterRoom + nomeUser;
        return pacchetto;
    }

    public String sendBootstrap(String nome) {
        pacchetto = bootstrap + nome;
        System.out.println("CLIENTPROTOCOL\tSto inviando: " + pacchetto);
        return pacchetto;
    }

    public String removeRoom(String ip) {
        pacchetto = roomHeader + remove_room + ip;
        System.out.println("CLIENTPROTOCOL\tSto inviando: " + pacchetto);
        return pacchetto;
    }

    public void playCard(String player, String carta, int position) {
        posizione_mancanteg1 = position;
        pacchetto = cardHeader + play_card + player + "." + carta + "." + position;
        System.out.println("CLIENTPROTOCOL\tSto inviando: " + pacchetto);
        clientThread.writeToServer(pacchetto);
    }

    public void restartGame(int nGiocatori) {
        pacchetto = gameHeader + restartGame + nGiocatori;
        System.out.println("CLIENTPROTOCOL\tRESTART GAME");
        clientThread.writeToServer(pacchetto);
    }

    //RICEVE DAL SERVER
    public void turnoGiocatore(String msg) {
        turno = getContent(msg);
        Main.new2PGame.turno = turno;
        System.out.println("CLIENT\tTurno di " + turno);
    }

    private int roomIsFull(String msg) {
        int n = 0;
        n = Integer.valueOf(getContentId(msg));
        if (n == 2) {
            Main.attesa.wait = false;
            Main.menu.getContentPane().removeAll();
            Main.menu.add(Main.new2PGame);
            Main.menu.pack();
            Main.new2PGame.validate();
            Main.new2PGame.repaint();
            Main.menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            Main.menu.getContentPane().removeAll();
        }
        return n;

    }

    private void setBriscola(String briscola) {
        Carta carta = stringToCarta(briscola);
        Main.new2PGame.briscola = carta.getImage();
        Main.new2PGame.repaint();
    }

    private Carta stringToCarta(String c) {
        int numero = Integer.valueOf(c.substring(0, 2));
        String seme = c.substring(2);
        try {
            Carta carta = new Carta(numero, seme);
            return carta;
        } catch (IOException ex) {
        }
        return null;
    }

    private void setPlayer(String player) {
        Main.new2PGame.player = player;
        System.out.println("CLIENT\tSono il giocatore " + player);
    }

    private void setMano(String mano) {
        Main.new2PGame.carta1 = stringToCarta(mano.substring(0, 3));
        Main.new2PGame.carta2 = stringToCarta(mano.substring(4, 7));
        Main.new2PGame.carta3 = stringToCarta(mano.substring(8));
        Main.new2PGame.imageG11 = Main.new2PGame.carta1.getImage();
        Main.new2PGame.imageG12 = Main.new2PGame.carta2.getImage();
        Main.new2PGame.imageG13 = Main.new2PGame.carta3.getImage();
        Main.new2PGame.repaint();
    }

    private void animateCard(String msg) {
        String player = msg.substring(0, 2);
        String card = msg.substring(3, 6);
        Carta carta = stringToCarta(card);
        int position = Integer.parseInt(msg.substring(7));
        if (player.equals("g1") && Main.new2PGame.player.equals("g2")
                || player.equals("g2") && Main.new2PGame.player.equals("g1")) {
            posizione_mancanteg2 = position;
        } else {
            posizione_mancanteg1 = position;
        }
        if (player.equals("g1") && Main.new2PGame.player.equals("g2")) {
            if (position == 1) {
                Main.new2PGame.cartax = Main.new2PGame.labelCartaG21.getLocationOnScreen().x;
                Main.new2PGame.cartay = Main.new2PGame.labelCartaG21.getLocationOnScreen().y;
                Main.new2PGame.imageG21 = carta.getImage();
                Main.new2PGame.cardG21played = true;
                Main.new2PGame.labelCartaG21.setIcon(null);
                Main.new2PGame.imageG21 = carta.getImage();
            }
            if (position == 2) {
                Main.new2PGame.cartax = Main.new2PGame.labelCartaG22.getLocationOnScreen().x;
                Main.new2PGame.cartay = Main.new2PGame.labelCartaG22.getLocationOnScreen().y;
                Main.new2PGame.imageG22 = carta.getImage();
                Main.new2PGame.cardG22played = true;
                Main.new2PGame.labelCartaG22.setIcon(null);
                Main.new2PGame.imageG22 = carta.getImage();
            }
            if (position == 3) {
                Main.new2PGame.cartax = Main.new2PGame.labelCartaG23.getLocationOnScreen().x;
                Main.new2PGame.cartay = Main.new2PGame.labelCartaG23.getLocationOnScreen().y;
                Main.new2PGame.imageG23 = carta.getImage();
                Main.new2PGame.cardG23played = true;
                Main.new2PGame.labelCartaG23.setIcon(null);
                Main.new2PGame.imageG23 = carta.getImage();
            }
        } else if (player.equals("g2") && Main.new2PGame.player.equals("g1")) {
            if (position == 1) {
                Main.new2PGame.cartax = Main.new2PGame.labelCartaG21.getLocationOnScreen().x;
                Main.new2PGame.cartay = Main.new2PGame.labelCartaG21.getLocationOnScreen().y;
                Main.new2PGame.imageG21 = carta.getImage();
                Main.new2PGame.cardG21played = true;
                Main.new2PGame.labelCartaG21.setIcon(null);
                Main.new2PGame.imageG21 = carta.getImage();
            }
            if (position == 2) {
                Main.new2PGame.cartax = Main.new2PGame.labelCartaG22.getLocationOnScreen().x;
                Main.new2PGame.cartay = Main.new2PGame.labelCartaG22.getLocationOnScreen().y;
                Main.new2PGame.imageG22 = carta.getImage();
                Main.new2PGame.cardG22played = true;
                Main.new2PGame.labelCartaG22.setIcon(null);
                Main.new2PGame.imageG22 = carta.getImage();
            }
            if (position == 3) {
                Main.new2PGame.cartax = Main.new2PGame.labelCartaG23.getLocationOnScreen().x;
                Main.new2PGame.cartay = Main.new2PGame.labelCartaG23.getLocationOnScreen().y;
                Main.new2PGame.imageG23 = carta.getImage();
                Main.new2PGame.cardG23played = true;
                Main.new2PGame.labelCartaG23.setIcon(null);
                Main.new2PGame.imageG23 = carta.getImage();
            }
        }
        Main.new2PGame.repaint();

    }

    private void animateWonRound(String msg) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        String winner = msg;
        Main.new2PGame.cardG1 = Main.new2PGame.getImage(Main.new2PGame.labelCartaGiocataG1.getIcon());
        Main.new2PGame.cardG2 = Main.new2PGame.getImage(Main.new2PGame.labelCartaGiocataG2.getIcon());
        Main.new2PGame.cartaGiocataG1x = Main.new2PGame.labelCartaGiocataG1.getLocationOnScreen().x;
        Main.new2PGame.cartaGiocataG2x = Main.new2PGame.labelCartaGiocataG2.getLocationOnScreen().x;
        Main.new2PGame.cartaGiocataG1y = Main.new2PGame.labelCartaGiocataG1.getLocationOnScreen().y;
        Main.new2PGame.cartaGiocataG2y = Main.new2PGame.labelCartaGiocataG2.getLocationOnScreen().y;
        if (winner.equals("g1") && Main.new2PGame.player.equals("g1")) {
            Main.new2PGame.prendiCartaG2 = true;
            Main.new2PGame.labelCartaGiocataG2.setIcon(null);
            Main.new2PGame.repaint();
        } else if (winner.equals("g1") && Main.new2PGame.player.equals("g2")) {
            Main.new2PGame.prendiCartaG1 = true;
            Main.new2PGame.labelCartaGiocataG1.setIcon(null);
            Main.new2PGame.repaint();
        } else if (winner.equals("g2") && Main.new2PGame.player.equals("g1")) {
            Main.new2PGame.prendiCartaG1 = true;
            Main.new2PGame.labelCartaGiocataG1.setIcon(null);
            Main.new2PGame.repaint();
        } else if (winner.equals("g2") && Main.new2PGame.player.equals("g2")) {
            Main.new2PGame.prendiCartaG2 = true;
            Main.new2PGame.labelCartaGiocataG2.setIcon(null);
            Main.new2PGame.repaint();
        }

        //metodo che decide cosa fare con tali info (calcolo del punteggio)
    }

    private void animatePescare(String card) throws IOException, InterruptedException {
        Carta carta = stringToCarta(card);
        if (Main.new2PGame.turno.equals("g1") && Main.new2PGame.player.equals("g1")) {
            Main.new2PGame.getMazzoCoordinates();
            Main.new2PGame.getMazzoCoordinates();
            calcolaCartaG1(carta);
            Main.new2PGame.repaint();
            Main.new2PGame.getMazzoCoordinates();
            if (posizione_mancanteg2 == 1) {
                Main.new2PGame.pescaG21 = true;
            } else if (posizione_mancanteg2 == 2) {
                Main.new2PGame.pescaG22 = true;
            } else if (posizione_mancanteg2 == 3) {
                Main.new2PGame.pescaG23 = true;
            }
            Main.new2PGame.repaint();
        } else if (Main.new2PGame.turno.equals("g1") && Main.new2PGame.player.equals("g2")) {
            Main.new2PGame.getMazzoCoordinates();
            if (posizione_mancanteg2 == 1) {
                Main.new2PGame.pescaG21 = true;
                Main.new2PGame.repaint();
                while (Main.new2PGame.pescaG21) {
                    Thread.sleep(1);
                }
                Main.new2PGame.getMazzoCoordinates();
                calcolaCartaG1(carta);
                Main.new2PGame.repaint();
            } else if (posizione_mancanteg2 == 2) {
                Main.new2PGame.pescaG22 = true;
                Main.new2PGame.repaint();
                while (Main.new2PGame.pescaG22) {
                    Thread.sleep(1);
                }
                Main.new2PGame.getMazzoCoordinates();
                calcolaCartaG1(carta);
                Main.new2PGame.repaint();
            } else if (posizione_mancanteg2 == 3) {
                Main.new2PGame.pescaG23 = true;
                Main.new2PGame.repaint();
                while (Main.new2PGame.pescaG23) {
                    Thread.sleep(1);
                }
                Main.new2PGame.getMazzoCoordinates();
                calcolaCartaG1(carta);
                Main.new2PGame.repaint();
            }
        } else if (Main.new2PGame.turno.equals("g2") && Main.new2PGame.player.equals("g1")) {
            Main.new2PGame.getMazzoCoordinates();
            if (posizione_mancanteg2 == 1) {
                Main.new2PGame.pescaG21 = true;
                Main.new2PGame.repaint();
                while (Main.new2PGame.pescaG21) {
                    Thread.sleep(1);
                }
                Main.new2PGame.getMazzoCoordinates();
                calcolaCartaG1(carta);
                Main.new2PGame.repaint();
            } else if (posizione_mancanteg2 == 2) {
                Main.new2PGame.pescaG22 = true;
                Main.new2PGame.repaint();
                while (Main.new2PGame.pescaG22) {
                    Thread.sleep(1);
                }
                Main.new2PGame.getMazzoCoordinates();
                calcolaCartaG1(carta);
                Main.new2PGame.repaint();
            } else if (posizione_mancanteg2 == 3) {
                Main.new2PGame.pescaG23 = true;
                Main.new2PGame.repaint();
                while (Main.new2PGame.pescaG23) {
                    Thread.sleep(1);
                }
                Main.new2PGame.getMazzoCoordinates();
                calcolaCartaG1(carta);
                Main.new2PGame.repaint();
            }
        } else if (Main.new2PGame.turno.equals("g2") && Main.new2PGame.player.equals("g2")) {
            Main.new2PGame.getMazzoCoordinates();
            calcolaCartaG1(carta);
            Main.new2PGame.repaint();
            Main.new2PGame.getMazzoCoordinates();
            if (posizione_mancanteg2 == 1) {
                Main.new2PGame.pescaG21 = true;
            } else if (posizione_mancanteg2 == 2) {
                Main.new2PGame.pescaG22 = true;
            } else if (posizione_mancanteg2 == 3) {
                Main.new2PGame.pescaG23 = true;
            }
            Main.new2PGame.repaint();
        }

    }

    private void cancellaMazzo() {
        Main.new2PGame.disegnaMazzo = false;
        Main.new2PGame.repaint();
    }

    private void cancellaBriscola() {
        Main.new2PGame.disegnaBriscola = false;
        Main.new2PGame.repaint();
    }

    private void calcolaCartaG1(Carta carta) {
        try {
            if (posizione_mancanteg1 == 1) {
                Main.new2PGame.carta1 = carta;
                Main.new2PGame.imageG11 = carta.getImage();
                Main.new2PGame.pescaG11 = true;
                while (Main.new2PGame.pescaG11) {
                    Thread.sleep(1);
                }
            } else if (posizione_mancanteg1 == 2) {
                Main.new2PGame.carta2 = carta;
                Main.new2PGame.imageG12 = carta.getImage();
                Main.new2PGame.pescaG12 = true;
                while (Main.new2PGame.pescaG12) {
                    Thread.sleep(1);
                }
            } else if (posizione_mancanteg1 == 3) {

                Main.new2PGame.carta3 = carta;
                Main.new2PGame.imageG13 = carta.getImage();
                Main.new2PGame.pescaG13 = true;
                while (Main.new2PGame.pescaG13) {
                    Thread.sleep(1);
                }

            }

        } catch (InterruptedException ex) {
        }
    }

    private void finisciPartita() {
        Main.new2PGame.finisci = true;
    }

    private void disegnaPunti(String punti) {
        System.out.println("CLIENTPROTOCOL\tCambio punti: " + punti);
        String[] parti = punti.split(Pattern.quote("."));
        String p1 = parti[0];
        String p2 = parti[1];
        int punti1 = 0;
        int punti2 = 0;
        if (Main.new2PGame.player.equals("g1")) {
            punti1 = Integer.parseInt(p1);
            punti2 = Integer.parseInt(p2);
        } else {
            punti2 = Integer.parseInt(p1);
            punti1 = Integer.parseInt(p2);
        }
        System.out.println("PUNTI: " + p1 + " " + p2);
        Main.new2PGame.puntiG1 = punti1;
        Main.new2PGame.puntiG2 = punti2;
        Main.new2PGame.repaint();
    }
    public String sendCardBg(int nro) {
        //pacchetto = cardBg + "rcv";
        pacchetto = cardBg + nro;
        System.out.println("CLIENTPROTOCOL\tSto inviando: " + pacchetto);
        return pacchetto;
        
    }
    private void reciveCardBg(String msg) {
        Main.new2PGame.bg = Integer.valueOf(getContent(msg));
    }

}
