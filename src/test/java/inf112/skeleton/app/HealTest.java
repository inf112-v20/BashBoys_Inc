package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inf112.skeleton.app.object.HealDraw;
import inf112.skeleton.app.object.Robot;

class HealTest {

    @Test
    void healHealngTest(){
        Board board = new Board(12,12);
        Robot r = new Robot("Kåre");
        board.addItem(r, 5, 5);
        board.addItem(new HealDraw(false), 5, 5);
        r.dmg(5);//4HP left
        board.healDo();
        assertEquals(r.getHp(),5);
    }
    
    @Test
    void noOverHealTest(){
        Board board = new Board(12,12);
        Robot r = new Robot("Karl");
        board.addItem(r, 5, 5);
        board.addItem(new HealDraw(false), 5, 5);
        board.healDo();
        assertEquals(r.getHp(),9);
    }

}
