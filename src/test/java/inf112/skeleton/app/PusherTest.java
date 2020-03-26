package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.object.Pusher;
import inf112.skeleton.app.object.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class PusherTest {

    Board b;
    Robot r;
    
    @BeforeEach
    void init(){
        b = new Board(12,12);
        r = new Robot("Kåre");
    }

    @Test
    @DisplayName("135Pusher Pushing At Right Phase")
    void pusher123PushingTest(){
        Pusher p = new Pusher(Direction.WEST, true);
        b.addItem(r, 5, 5);
        b.addItem(p, 6, 5);
        b.pushAll(1);
        assertEquals(r.getX(),4);
    }
    
    @Test
    @DisplayName("135Pusher Not Pushing At Phase")
    void pusher123NotPushingTest(){
        Pusher p = new Pusher(Direction.WEST, true);
        b.addItem(r, 5, 5);
        b.addItem(p, 6, 5);
        b.pushAll(2);
        assertEquals(r.getX(),5);
    }
    
    @Test
    @DisplayName("24Pusher Pushing At Right Phase")
    void pusher24PushingTest(){
        Pusher p = new Pusher(Direction.WEST, false);
        b.addItem(r, 5, 5);
        b.addItem(p, 6, 5);
        b.pushAll(1);
        assertEquals(r.getX(),5);
    }
    
    @Test
    @DisplayName("24Pusher Not Pushing At Phase")
    void pusher24NotPushingTest(){
        Pusher p = new Pusher(Direction.WEST, false);
        b.addItem(r, 5, 5);
        b.addItem(p, 6, 5);
        b.pushAll(2);
        assertEquals(r.getX(),4);
    }
}
