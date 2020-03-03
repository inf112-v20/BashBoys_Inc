package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Belt;
import inf112.skeleton.app.object.Robot;

class BeltPushingTests {

    @Test
    @DisplayName("Belt pushing robot")
    void singleBeltPushingSingleRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH), 5, 5);
        Robot r = new Robot("Jorgen");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),6);
    }
    
    @Test
    @DisplayName("Double belts pushing a robots")
    void twoDoubleBeltsPushingSingleRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new Belt(Direction.NORTH,2), 5, 6);
        Robot r = new Robot("JLoh");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),7);
    }
    
    @Test
    @DisplayName("Single belt pushing robot onto double belt but not more")
    void singleThenDoubleBelt() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH), 5, 5);
        board.addItem(new Belt(Direction.NORTH,2), 5, 6);
        Robot r = new Robot("JLoh");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),6);
    }
    
    @Test
    @DisplayName("Testing that corner turns robot")
    void cornerBeltTurningRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new Belt(Direction.EAST,2,LeftRight.RIGHT), 5, 6);
        Robot r = new Robot("JLoh");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),6);
        assertEquals(r.getX(),6);
        assertEquals(r.getDir(),Direction.WEST);
    }

}
