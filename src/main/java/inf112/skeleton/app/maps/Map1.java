package inf112.skeleton.app.maps;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.HealDraw;
import inf112.skeleton.app.object.Hole;
import inf112.skeleton.app.object.Wall;
import inf112.skeleton.app.object.belts.Belt;
import inf112.skeleton.app.object.belts.CornerBelt;
import inf112.skeleton.app.object.belts.CornerJoinBelt;

public class Map1 {
    Direction west = Direction.WEST;
    Direction east = Direction.EAST;
    Direction north = Direction.NORTH;
    Direction south = Direction.SOUTH;
    LeftRight left = LeftRight.LEFT;
    LeftRight right = LeftRight.RIGHT;
    public Map1(Board board) {
        // y=0
        board.addItem(new Hole(), 0, 0);
        board.addItem(new Belt(south), 1, 0);
        board.addItem(new Wall(south), 2, 0);
        board.addItem(new Wall(south), 4, 0);
        board.addItem(new Belt(south), 5, 0);
        board.addItem(new Belt(north), 6, 0);
        board.addItem(new Wall(south), 7, 0);
        board.addItem(new Wall(east), 7, 0);
        board.addItem(new Wall(south), 9, 0);
        board.addItem(new Belt(north), 10, 0);

        // y=1
        board.addItem(new Belt(east), 0, 1);
        board.addItem(new CornerBelt(south, right), 1, 1);
        board.addItem(new Hole(), 2, 1);
        board.addItem(new Belt(south), 5, 1);
        board.addItem(new CornerJoinBelt(north, right), 6, 1);
        board.addItem(new Belt(west), 7, 1);
        board.addItem(new Belt(west), 8, 1);
        board.addItem(new Belt(west), 9, 1);
        board.addItem(new CornerBelt(west, left), 10, 1);

        // y=2
        board.addItem(new Wall(west), 0, 2);
        board.addItem(new HealDraw(false), 0, 2);
        board.addItem(new Wall(south), 2, 2);
        board.addItem(new Belt(south), 5, 2);
        board.addItem(new Belt(north), 6, 2);
        board.addItem(new Wall(west), 8, 2);
        board.addItem(new Wall(east), 11, 2);

        // y=3
        board.addItem(new Wall(west, 1), 2, 3);
        board.addItem(new Wall(west), 4, 3);
        board.addItem(new Wall(north), 4, 3);
        board.addItem(new Belt(south), 5, 3);
        board.addItem(new Belt(north), 6, 3);
        board.addItem(new Wall(west, 1), 7, 3);
        board.addItem(new Wall(east), 8, 3);
        board.addItem(new Hole(), 9, 3);

        // y = 4
        board.addItem(new Wall(west), 0, 4);
        board.addItem(new Wall(south), 1, 4);
        board.addItem(new Wall(south), 3, 4);
        board.addItem(new CornerBelt(east, left), 4, 4);
        board.addItem(new CornerBelt(south, right), 5, 4);
        board.addItem(new Belt(north), 6, 4);
        board.addItem(new Wall(west), 7, 4);
        board.addItem(new Wall(north), 7, 4);
        board.addItem(new Wall(south), 10, 4);
        board.addItem(new Wall(east), 11, 4);

        // y=5
        board.addItem(new Belt(east), 0, 5);
        board.addItem(new Belt(east), 1, 5);
        board.addItem(new Belt(east), 2, 5);
        board.addItem(new Belt(east), 3, 5);
        board.addItem(new CornerBelt(south, right), 4, 5);
        board.addItem(new Hole(), 5, 5);
        board.addItem(new CornerBelt(east, right), 6, 5);
        board.addItem(new Belt(east), 7, 5);
        board.addItem(new Belt(east), 8, 5);
        board.addItem(new Belt(east), 9, 5);
        board.addItem(new Belt(east), 10, 5);
        board.addItem(new Belt(east), 11, 5);

        // y=6
        board.addItem(new Belt(west), 0, 6);
        board.addItem(new Belt(west), 1, 6);
        board.addItem(new Belt(west), 2, 6);
        board.addItem(new CornerBelt(west,right), 3, 6);
        board.addItem(new Hole(), 4, 6);
        board.addItem(new Hole(), 5, 6);
        board.addItem(new Hole(), 6, 6);
        board.addItem(new CornerBelt(north,right), 7, 6);
        board.addItem(new Belt(west), 8, 6);
        board.addItem(new Belt(west), 9, 6);
        board.addItem(new Belt(west), 10, 6);
        board.addItem(new Belt(west), 11, 6);
        
        //y=7
        board.addItem(new Wall(west), 0, 7);
        board.addItem(new Hole(), 1, 7);
        board.addItem(new Hole(), 2, 7);
        board.addItem(new CornerBelt(south,left), 3, 7);
        board.addItem(new CornerBelt(west,right), 4, 7);
        board.addItem(new Hole(), 5, 7);
        board.addItem(new CornerBelt(north,right), 6, 7);
        board.addItem(new CornerBelt(west,left), 7, 7);
        board.addItem(new Wall(south), 9, 7);
        board.addItem(new Wall(east), 11, 7);
        
        //y=8
        board.addItem(new Wall(south), 1, 8);
        board.addItem(new Wall(east), 1, 8);
        board.addItem(new HealDraw(true), 3, 8);
        board.addItem(new CornerBelt(south,left), 4, 8);
        board.addItem(new CornerBelt(west,right), 5, 8);
        board.addItem(new Belt(north), 6, 8);
        board.addItem(new Wall(south), 7, 8);
        board.addItem(new Wall(west), 7, 8);
        board.addItem(new Wall(south,2), 8, 8);
        board.addItem(new Wall(south), 10, 8);
        
        //y=9
        board.addItem(new Wall(west), 0, 9);
        board.addItem(new Wall(south,1), 4, 9);
        board.addItem(new Belt(south), 5, 9);
        board.addItem(new Belt(north), 6, 9);
        board.addItem(new Wall(west), 7, 9);
        board.addItem(new Hole(), 9, 9);
        board.addItem(new Wall(east), 11, 9);
        
        //y=10
        board.addItem(new CornerBelt(east,left), 1, 10);
        board.addItem(new Belt(east), 2, 10);
        board.addItem(new Belt(east), 3, 10);
        board.addItem(new Belt(east), 4, 10);
        board.addItem(new CornerJoinBelt(south,right), 5, 10);
        board.addItem(new Belt(north), 6, 10);
        board.addItem(new Wall(east), 9, 10);
        board.addItem(new Wall(north), 9, 10);
        board.addItem(new CornerBelt(north,right), 10, 10);
        board.addItem(new Belt(west), 11, 10);
        
        //y=11
        board.addItem(new Belt(south), 1, 11);
        board.addItem(new Wall(north), 2, 11);
        board.addItem(new Wall(north), 4, 11);
        board.addItem(new Belt(south), 5, 11);
        board.addItem(new Belt(north), 6, 11);
        board.addItem(new Wall(north), 7, 11);
        board.addItem(new Wall(south), 8, 11);
        board.addItem(new Wall(north), 9, 11);
        board.addItem(new Belt(north), 10, 11);
        board.addItem(new HealDraw(false), 11, 11);
    }
}
