package inf112.skeleton.app;

import inf112.skeleton.app.object.Flag;
import inf112.skeleton.app.object.Robot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RespawnTests {

    @Test
    @DisplayName("Respawn at flag test")
    void respawnAtFlagTest(){
        Flag f1 = new Flag(1,1,"F1");
        Robot r = new Robot("Rebo");
        Game g = new Game();
        Player p = new Player("Bab",r);
        p.setSpawn(f1);
        g.players().add(p);
        g.getBoard().addItem(r, 5, 5);
        g.getBoard().addItem(f1, 1, 1);
        assertEquals(r.getX(),5);
        assertEquals(r.getY(),5);
        r.dmg(9);
        g.respawn();
        assertEquals(r.getX(),1);
        assertEquals(r.getY(),1);
    }

}
