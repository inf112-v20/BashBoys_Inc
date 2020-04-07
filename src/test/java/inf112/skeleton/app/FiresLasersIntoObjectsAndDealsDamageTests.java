package inf112.skeleton.app;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;

class FiresLasersIntoObjectsAndDealsDamageTests {
    
    Board b;
    
    @BeforeEach
    void init() {
        b = new Board(12, 12);
    }

    @Test
    @DisplayName("WallLaser and Robot fires on empty board and doesn't crash the game test")
    public void wallLaserAndRobotFiresOnEmptyBoadWithNoCrashTest() {
        b.addItem(new Wall(Direction.NORTH,1), 5,11);
        b.addItem(new Robot("Karl Erik", Direction.EAST), 0, 0);
        b.fireLasers(); //Should not return any error
    }
    
    @Test
    @DisplayName("Robot gets hit by double laser")
    public void robotGetsHitByWallLaserTest() {
        b.addItem(new Wall(Direction.NORTH,2), 5,11);
        Robot jens = new Robot("Jens");
        b.addItem(jens, 5, 5);
        b.fireLasers();
        
        assertEquals(jens.getHp(),7);
    }
    
    @Test
    @DisplayName("Two Robots Hits Each Other With Laser Attacks Test")
    public void twoRobotsShootsEachOtherTest() {
        Robot grete = new Robot("Grete",Direction.WEST);
        Robot hans = new Robot("Hans",Direction.EAST);
        b.addItem(hans, 0, 6);
        b.addItem(grete, 11, 6);
        b.fireLasers();
        
        assertEquals(hans.getHp(),8);
        assertEquals(grete.getHp(),8);
    }
    
    @Test
    @DisplayName("Only one robot takes dmg")
    public void onlyOneRobotTakesDamage() {
        Robot tobias = new Robot("Tobias");
        b.addItem(new Wall(Direction.NORTH,2), 5,11);
        Robot petter = new Robot("Petter");
        petter.dmg(8);
        
        b.addItem(petter, 5, 5);
        b.addItem(tobias, 5, 4);
        
        b.fireLasers();
        assertEquals(tobias.getHp(),9);
    }

}
