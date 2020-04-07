package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
import inf112.skeleton.app.object.belts.Belt;
import inf112.skeleton.app.object.belts.CornerBelt;

class BeltPushingTests {

    @Test
    @DisplayName("Belt pushing robot")
    public void singleBeltPushingSingleRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH), 5, 5);
        Robot r = new Robot("Jorgen");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),6);
    }
    
    @Test
    @DisplayName("Double belts pushing a robots")
    public void twoDoubleBeltsPushingSingleRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new Belt(Direction.NORTH,2), 5, 6);
        Robot r = new Robot("JLoh1");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),7);
    }
    
    @Test
    @DisplayName("Single belt pushing robot onto double belt but not more")
    public void singleThenDoubleBelt() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH), 5, 5);
        board.addItem(new CornerBelt(Direction.NORTH, 2, LeftRight.RIGHT), 5, 6);
        Robot r = new Robot("JLoh2");
        board.addItem(r, 5, 5);
        
        board.moveBelts();
        assertEquals(r.getY(),6);
    }
    
    @Test
    @DisplayName("Testing that corner turns robot")
    public void cornerBeltTurningRobot() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new CornerBelt(Direction.EAST,2,LeftRight.RIGHT), 5, 6);
        Robot r = new Robot("JLoh3");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),6);
        assertEquals(r.getX(),6);
        assertEquals(r.getDir(),Direction.WEST);
    }

    @Test
    @DisplayName("Testing wall blocking belt")
    void wallBlockBeltTest() {
        Board board = new Board(12, 12);
        board.addItem(new Belt(Direction.NORTH,2), 5, 5);
        board.addItem(new Belt(Direction.EAST,2), 5, 6);
        board.addItem(new Wall(Direction.SOUTH), 5, 6);
        Robot r = new Robot("JLoh3");
        board.addItem(r, 5, 5);
        board.moveBelts();
        assertEquals(r.getY(),5);
        assertEquals(r.getX(),5);
    }
}
