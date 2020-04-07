package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import inf112.skeleton.app.ai.Ai;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.MoveCard;
import inf112.skeleton.app.cards.Nothing;
import inf112.skeleton.app.cards.ShutDown;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.interfaces.ICard;
import inf112.skeleton.app.interfaces.IPlayer;
import inf112.skeleton.app.maps.Map1;
import inf112.skeleton.app.object.Flag;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.SpawnPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GameClass {

    private Board board;
    private int player;
    private ArrayList<Player> players;
    private Deck deck = new Deck();
    boolean t = false;
    ArrayList<Flag> flags = new ArrayList<>();
    ArrayList<SpawnPoint> spawns = new ArrayList<>();

    ArrayList<ICard> turn;
    ViewEngine view;

    private String map = "map1";
    private boolean won;
    String winner;
    LwjglApplicationConfiguration cfg;

    Direction west = Direction.WEST;
    Direction east = Direction.EAST;
    Direction north = Direction.NORTH;
    Direction south = Direction.SOUTH;
    LeftRight left = LeftRight.LEFT;
    LeftRight right = LeftRight.RIGHT;

    public GameClass() {
        this.board = new Board(12, 15);
        players = new ArrayList<Player>();
    }

    /**
     * Returns currentPlayer
     * 
     * @return currentPlayer as player
     */
    public Player getPlayer(){
        return players.get(player);
    }

    public void play(LwjglApplicationConfiguration cfg){
        this.cfg = cfg;
        view = new ViewEngine(this);
        new LwjglApplication(view, this.cfg);

        while (!t) {
            sleep(1);
            // System.out.println("");
        }

        getMap();

        if (player == 9) {
            Robot r2 = new Robot("robot 2");
            Robot r3 = new Robot("robot 3");
            board.addItem(r2, 10, 2);
            board.addItem(r3, 4, 2);
            Ai danny = new Ai("DannyDeVito", r2);
            Ai karl = new Ai("lil curly", r3);
            players.add(danny);
            players.add(karl);
        }
        while (t) {
            winCheck();
            if (getPlayer().getShutdown() != 0) {
                getPlayer().addCardToSheet(new ShutDown(getPlayer()));
                getPlayer().addCardToSheet(new ShutDown(getPlayer()));
                getPlayer().addCardToSheet(new ShutDown(getPlayer()));
                getPlayer().addCardToSheet(new ShutDown(getPlayer()));
                getPlayer().addCardToSheet(new ShutDown(getPlayer()));
                getPlayer().setReady(true);
                if (player != 0) {
                    sendHand();
                }
            } else {
                for (Player p : players) {
                    p.giveCard(deck.getCards(Math.min(9, p.getRobot().getHp())));
                }
            }
            programmingPhase();
            respawn();

        }
    }

    private void winCheck(){
        boolean won = false;
        for (Player p : players) {
            if (p.getFlags().size() == flags.size()) {
                won = true;
            }
            if (won) {
                System.out.println(p.getName() + " won!!!");
                this.won = true;
                this.winner = p.getName();
            }
        }
    }

    public void programmingPhase(){
        boolean all_ready = false;

        while (!all_ready) {
            all_ready = true;
            for (Player player : players) {
                for (IPlayer p : players) {
                    if (!p.isReady()) {
                        all_ready = false;
                    }
                    // Ai does move if any.
                    if (player instanceof Ai && !player.isReady()) {
                        player.giveCard(deck.getCards(9));
                        ((Ai) player).setMoves();
                        player.setReady(true);
                    }
                }
            }
        }
        programmingMove();
        for (Player pl : players) {
            if (pl.getShutdown() > 0) {
                pl.setShutDow(pl.getShutdown() - 1);
                if (pl.getShutdown() == 0) {
                    pl.getRobot().heal(9);
                }
            }
            pl.clearSheet();
            pl.setReady(false);
            pl.clearHand();
        }

    }

    public void programmingMove(){
        turn = new ArrayList<ICard>();
        // for (Player p : players)
        // p.setSpawn(flag1);
        for (int card_nr = 0; card_nr < 5; card_nr++) {
            ICard[] priority = new ICard[players.size()];
            for (Player p : players) {
                int i = -1;
                while (priority[++i] != null && p.getCardFromSheet(card_nr).getPriority() < priority[i].getPriority())
                    ;
                if (priority[i] == null)
                    priority[i] = p.getCardFromSheet(card_nr);
                else {
                    ICard next = p.getCardFromSheet(card_nr);
                    ICard temp = priority[i];
                    while (priority[i] != null) {
                        temp = priority[i];
                        priority[i] = next;
                        next = temp;
                        i++;
                    }
                    priority[i] = next;
                }
            }
            for (ICard c : Arrays.asList(priority)) {
                if (priority[0] != null)
                    System.out.println(c.getName());
            }
            if (priority[0] != null)
                turn.addAll(Arrays.asList(priority));
            // sleep(1);
        }
        for (ICard c : turn) {
            System.out.println(turn.size());
            System.out.println(c.getName() + ", " + c.getPriority());
        }
        doStuf();
    }

    /**
     * returns list of all players in game
     * 
     * @return list of players in game
     */
    public ArrayList<Player> players(){
        return players;
    }

    /**
     * Board that is played on
     * 
     * @return the board being played on
     */
    public Board getBoard(){
        return board;
    }

    /**
     * Pauses program for given time
     * 
     * @param seconds - Time too pause for in second
     */
    public void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Respawns all player robots that are dead
     */
    public void respawn(){
        for (Player p : players) {
            if (p.getRobot() != null && p.getRobot().isDead() && p.getSpawn() != null) {
                if (board.getRobots().contains(p.getRobot()))
                    board.removeItem(p.getRobot());
                p.getRobot().setHp(9);
                int x = p.getSpawn().getX();
                int y = p.getSpawn().getY();
                p.getRobot().setX(x);
                p.getRobot().setY(y);
                board.addItem(p.getRobot(), x, y);
            }
        }
    }

    /**
     * Respawns given players robot if dead
     * 
     * @param p - player too revive robot
     */
    public void respawn(Player p){
        if (p.getRobot().isDead() && p.getSpawn() != null) {
            if (board.getRobots().contains(p.getRobot()))
                board.removeItem(p.getRobot());
            p.getRobot().setHp(9);
            int x = p.getSpawn().getX();
            int y = p.getSpawn().getY();
            p.getRobot().setX(x);
            p.getRobot().setY(y);
            board.addItem(p.getRobot(), x, y);
        }
    }

    public void doStuf(){
        tDo();
        if (player == 0) {
            String send = "";
            for (ICard c : turn) {
                System.out.println(c.getName());
                System.out.println(str2(c));
                send += str2(c);
            }
            send = send.substring(0, send.length() - 1);
            send += "\n";
            SocketHints sh = new SocketHints();
            sh.connectTimeout = 10000;
            ArrayList<Socket> socks = new ArrayList<>();
            for (int i = 1; i < players.size(); i++) {
                if (players.get(i) instanceof Ai)
                    continue;
                socks.add(Gdx.net.newClientSocket(Protocol.TCP, players.get(i).getIP(), players.get(i).getPort(), sh));
            }
            for (Socket sss : socks) {
                try {
                    sss.getOutputStream().write(send.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void sendHand(){
        String send = player + ",";

        for (ICard c : getPlayer().getProgramSheet()) {
            send += str(c);
        }

        send = send.substring(0, send.length() - 1);
        send += "\n";
        SocketHints sh = new SocketHints();
        sh.connectTimeout = 10000;
        Socket s = Gdx.net.newClientSocket(Protocol.TCP, players.get(0).getIP(), players.get(0).getPort(), sh);
        try {
            s.getOutputStream().write(send.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String str2(ICard c){
        String send = c.getPlayer().getID() + "," + c.getPriority() + "";
        if (c.getName() == "Back Up") {
            send += "," + 1 + "," + 4 + ",";
        } else if (c instanceof ShutDown) {
            send += "," + 3 + "," + 0 + ",";
        } else if (c instanceof MoveCard) {
            send += "," + 1 + "," + c.getName().charAt(c.getName().length() - 1) + ",";
        } else if (c instanceof Nothing) {
            send += "," + 4 + "," + 0 + ",";
        } else if (c.getName() == "Rotate Right") {
            send += "," + 2 + "," + "1,";
        } else if (c.getName() == "Rotate Left") {
            send += "," + 2 + "," + "2,";
        } else {
            send += "," + 2 + "," + "3,";
        }
        return send;
    }

    private String str(ICard c){
        String send = c.getPriority() + "";
        if (c.getName().equals("Back Up")) {
            send += "," + 1 + "," + 4 + ",";
        } else if (c instanceof MoveCard) {
            send += "," + 1 + "," + c.getName().charAt(c.getName().length() - 1) + ",";
        } else if (c instanceof ShutDown) {
            send += "," + 3 + "," + 0 + ",";
        } else if (c instanceof Nothing) {
            send += "," + 4 + "," + 0 + ",";
        } else if (c.getName().equals("Rotate Right")) {
            send += "," + 2 + "," + "1,";
        } else if (c.getName().equals("Rotate Left")) {
            send += "," + 2 + "," + "2,";
        } else {
            send += "," + 2 + "," + "3,";
        }
        return send;
    }

    public void setPlayer(int i){
        player = i;
    }

    public void t(){
        t = true;
    }

    public void tDo(){
        int a = 0;
        for (Player p : players)
            if (p.getShutdown() > 0)
                a++;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < players.size() - a; i++) {
                turn.get(j * (players.size()) + i).doStuff(board);
                // System.out.println(turn.get(j*(players.size())+i).getName());
            }
            board.turnStuff(j + 1);
            sleep(1);
        }

    }

    public boolean won(){
        return won;
    }

    public String winner(){
        return winner;
    }

    public void map1(){
        // Flags
        Flag flag1 = new Flag("flag1", 1);
        Flag flag2 = new Flag("flag2", 2);
        flags = new ArrayList<Flag>();
        flags.add(flag1);
        flags.add(flag2);
        board.addItem(flag1, 2, 4);
        board.addItem(flag2, 8, 7);
        /**/
        SpawnPoint s1 = new SpawnPoint();
        SpawnPoint s2 = new SpawnPoint();
        SpawnPoint s3 = new SpawnPoint();
        SpawnPoint s4 = new SpawnPoint();
        SpawnPoint s5 = new SpawnPoint();
        SpawnPoint s6 = new SpawnPoint();
        SpawnPoint s7 = new SpawnPoint();
        SpawnPoint s8 = new SpawnPoint();

        board.addItem(s1, 1, 14);
        board.addItem(s2, 2, 13);
        board.addItem(s3, 4, 14);
        board.addItem(s4, 5, 13);
        board.addItem(s5, 7, 14);
        board.addItem(s6, 8, 13);
        board.addItem(s7, 10, 14);
        board.addItem(s8, 11, 13);

        spawns.addAll(Arrays.asList(new SpawnPoint[] { s1, s2, s3, s4, s5, s6, s7, s8 }));
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setSpawn(spawns.get(i));
            board.removeItem(players.get(i).getRobot());
            players.get(i).getRobot().dmg(9);
            respawn(players.get(i));
        }

        new Map1(board);
    }

    public void setMap(String s){
        map = s;
    }

    public void getMap(){
        switch (map) {
        case "map1":
            map1();
            break;
        }
    }
}
