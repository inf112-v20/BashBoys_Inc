package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Gear;
import inf112.skeleton.app.object.Robot;

class GearTurningTest {

    @Test
    public void leftGearTurn(){
        Board b = new Board(12,12);
        Robot r = new Robot("Finn",Direction.NORTH);
        Gear g = new Gear(LeftRight.LEFT);
        b.addItem(r, 5, 5);
        b.addItem(g, 5, 5);
        b.gearsDo();
        assertEquals(r.getDir(),Direction.WEST);
    }
    
    @Test
    public void rightGearTurn(){
        Board b = new Board(12,12);
        Robot r = new Robot("Jake",Direction.NORTH);
        Gear g = new Gear(LeftRight.RIGHT);
        b.addItem(r, 5, 5);
        b.addItem(g, 5, 5);
        b.gearsDo();
        assertEquals(r.getDir(),Direction.EAST);
    }

}
