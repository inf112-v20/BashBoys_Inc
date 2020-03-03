package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
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
    @DisplayName("Two belts pushing a robots")
    void twoBeltsPushingSingleRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH), 5, 5);
        board.addItem(new Belt(Direction.NORTH), 5, 6);
        Robot r = new Robot("JLoh");
        board.addItem(r, 5, 5);
        board.moveBelts();
        board.moveBelts();
        board.moveBelts();
        assertEquals(r.getY(),7);
    }

}
