package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Gear;
import inf112.skeleton.app.object.Hole;
import inf112.skeleton.app.object.Pusher;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
import inf112.skeleton.app.object.belts.Belt;
import inf112.skeleton.app.object.belts.CornerBelt;
import inf112.skeleton.app.object.belts.CornerJoinBelt;
import inf112.skeleton.app.object.belts.MergeBelt;

import java.util.concurrent.TimeUnit;

public class Game {

    private Board board;
    private int curentPlayer;
    private Player[] players;

    public Game() {
        this.board = new Board(12, 12);
        players = new Player[8];
    }
    
    public Player getPlayer(){
        return players[curentPlayer%8];
    }
    
    public void nextPlayer() {
        int failSwitch = 0;
        while(players[++curentPlayer%8] == null) {
            if(++failSwitch > players.length)throw new IllegalArgumentException("No more players");
        }
    }

    public void play(LwjglApplicationConfiguration cfg) {
        //Robots
        Robot r = new Robot("robot 1",Direction.EAST);
        Robot r2 = new Robot("robot 5");
        Robot r3 = new Robot("robot 2");
        
        //Players
        players[0] = new Player("Bob",r3);
        players[1] = new Player("Jon",r2);
        
        new LwjglApplication(new ViewEngine(this), cfg);
        
        //Shortcuts
        Direction west = Direction.WEST;
        Direction east = Direction.EAST;
        Direction north = Direction.NORTH;
        Direction south = Direction.SOUTH;
        
        LeftRight left = LeftRight.LEFT;
        LeftRight right = LeftRight.RIGHT;
        
        //Add robots
        board.addItem(r, 7, 5);
        board.addItem(r2, 11, 5);
        board.addItem(r3, 10, 0);
        
        //For displaying that all tiles work
        /*
        board.addItem(new CornerBelt(north,2,right), 0, 0);
        board.addItem(new CornerBelt(south,2,right), 0, 1);
        board.addItem(new CornerBelt(east,2,right), 0, 2);
        board.addItem(new CornerBelt(west,2,right), 0, 3);
        board.addItem(new CornerBelt(north,2,left), 0, 4);
        board.addItem(new CornerBelt(south,2,left), 0, 5);
        board.addItem(new CornerBelt(east,2,left), 0, 6);
        board.addItem(new CornerBelt(west,2,left), 0, 7);
        
        board.addItem(new CornerJoinBelt(north,2,right), 1, 0);
        board.addItem(new CornerJoinBelt(south,2,right), 1, 1);
        board.addItem(new CornerJoinBelt(east,2,right), 1, 2);
        board.addItem(new CornerJoinBelt(west,2,right), 1, 3);
        board.addItem(new CornerJoinBelt(north,2,left), 1, 4);
        board.addItem(new CornerJoinBelt(south,2,left), 1, 5);
        board.addItem(new CornerJoinBelt(east,2,left), 1, 6);
        board.addItem(new CornerJoinBelt(west,2,left), 1, 7);
        
        board.addItem(new Belt(north,2), 2, 0);
        board.addItem(new Belt(south,2), 2, 1);
        board.addItem(new Belt(east,2), 2, 2);
        board.addItem(new Belt(west,2), 2, 3);
        board.addItem(new MergeBelt(north,2), 2, 4);
        board.addItem(new MergeBelt(south,2), 2, 5);
        board.addItem(new MergeBelt(east,2), 2, 6);
        board.addItem(new MergeBelt(west,2), 2, 7);
        
        board.addItem(new CornerBelt(north,1,right), 3, 0);
        board.addItem(new CornerBelt(south,1,right), 3, 1);
        board.addItem(new CornerBelt(east,1,right), 3, 2);
        board.addItem(new CornerBelt(west,1,right), 3, 3);
        board.addItem(new CornerBelt(north,1,left), 3, 4);
        board.addItem(new CornerBelt(south,1,left), 3, 5);
        board.addItem(new CornerBelt(east,1,left), 3, 6);
        board.addItem(new CornerBelt(west,1,left), 3, 7);
        
        board.addItem(new CornerJoinBelt(north,1,right), 4, 0);
        board.addItem(new CornerJoinBelt(south,1,right), 4, 1);
        board.addItem(new CornerJoinBelt(east,1,right), 4, 2);
        board.addItem(new CornerJoinBelt(west,1,right), 4, 3);
        board.addItem(new CornerJoinBelt(north,1,left), 4, 4);
        board.addItem(new CornerJoinBelt(south,1,left), 4, 5);
        board.addItem(new CornerJoinBelt(east,1,left), 4, 6);
        board.addItem(new CornerJoinBelt(west,1,left), 4, 7);
        
        board.addItem(new Belt(north,1), 5, 0);
        board.addItem(new Belt(south,1), 5, 1);
        board.addItem(new Belt(east,1), 5, 2);
        board.addItem(new Belt(west,1), 5, 3);
        board.addItem(new MergeBelt(north,1), 5, 4);
        board.addItem(new MergeBelt(south,1), 5, 5);
        board.addItem(new MergeBelt(east,1), 5, 6);
        board.addItem(new MergeBelt(west,1), 5, 7);
        */
        
        board.addItem(new Wall(north), 1, 1);
        board.addItem(new Wall(south), 1, 1);
        
        //Real tings
        //board.addItem(new Belt(north,2), 7, 5);
        board.addItem(new Belt(north,2), 7, 6);
        board.addItem(new Belt(north,1), 7, 7);
        board.addItem(new CornerBelt(east,2,LeftRight.RIGHT), 7, 8);
        board.addItem(new Hole(), 8, 8);
        board.addItem(new Wall(north), 7, 7);
        
        board.addItem(new Belt(west,2), 11, 8); //Display under wall
        board.addItem(new Belt(east,2), 10, 8); //Display under wall
        
        board.addItem(new Wall(east), 11, 8);
        board.addItem(new Wall(west), 10, 8);
                
        board.addItem(new Wall(north), 11, 7);
        board.addItem(new Wall(north), 10, 7);
        
        board.addItem(new Wall(south), 11, 9);
        board.addItem(new Wall(south), 10, 9);
        
        board.addItem(new Pusher(west,true), 8, 5);
        board.addItem(new Pusher(east,false), 5, 5);
        board.addItem(new Gear(LeftRight.RIGHT), 7, 5);
        //board.addItem(new Wall(west), 7, 5);
        //board.addItem(new Wall(east), 6, 5);
        
        int phase = 0;
        boolean t = true;
        while (t) {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            board.turnStuff((phase++%5)+1);
            
        }
    }
    
    private void gameLoop(){
        boolean gameWon = false;
        Player winner = null;
        while(!gameWon) {
            
            //Upgrade phase
            
            //Program phase
            
            for(int i = 1; i < 6;i++) {
                //Cards in phase i
                
                board.turnStuff(i);
                
            }
            
        }
        System.out.println(winner.getName() + " won the game");
    }
    
    public Board getBoard() {
        return board;
    }
}
