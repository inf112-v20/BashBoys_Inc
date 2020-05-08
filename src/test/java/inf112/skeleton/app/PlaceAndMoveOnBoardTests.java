package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.LeftRight;
import inf112.skeleton.app.object.Robot;
import inf112.skeleton.app.object.Wall;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class PlaceAndMoveOnBoardTests {

    @Test
    @DisplayName("Test that moves a robot into another robot that eventualy hits a wall and stops")
    /**
     * Dick tries to move his robot thrice but there is wall three tiles in the direction
     * there's also a robot in the way so he should only move twice and push the other robot into the wall
     */
    public void moveRobotOnIntoOtherRobotIntoWall() {
        Robot robot = new Robot("MainRobot"); // Faces SOUTH, (0,0)
        Board board = new Board(10, 10); // Has robot at (5,5)
        Robot robot2 = new Robot("BlockerBot");
        Wall wall = new Wall(5,7,Direction.NORTH);
        
        board.addItem(robot, 5, 4); // Robot now at (5,4)
        robot.turn(LeftRight.LEFT, 2); // Turns NORTH
        board.addItem(robot2, 5, 5);//Robot to push
        board.addItem(wall, wall.getX(), wall.getY()); //Wall to block/stop pushing
        board.moveItem(robot, 3); //Moves 2 up the BlockerBot blocked by wall
        
        //Checks the we actually stopped on wall
        assertEquals(robot.getY(),6);
        assertEquals(robot2.getY(),7);
        
        robot.turn(LeftRight.LEFT, 2); // Turns SOUTH
        robot.move(2);// Moves down
        assertTrue(robot.getX() == 5 && robot.getY() == 4);
    }

    @Test
    @DisplayName("Testing placing robot on board and checking that it's the right size")
    /**
     * Karen places her robot in the mid of an empty board
     */
    public void placeRobotOnBoardTest() {
        Board board = new Board(10, 10); // 10x10 board
        Robot robot = new Robot(); // Not on board yet
        board.addItem(robot, 5, 5); // Places robot
        assertTrue(board.getItems(5, 5).contains(robot)); // Checks if right robot
        assertEquals(board.getObjects().size(), 1); // Only that robot on board
    }
    
    @Test
    @DisplayName("Testing that reversing works like normal movment")
    /**
     * Bob reverses into another players robot then takes a uTurn and reverses back twice
     */
    public void reverseRobotOnBoardTest() {
        Robot robot = new Robot("Main"); // Faces SOUTH
        Robot robot2 = new Robot("Block");
        Board board = new Board(10, 10);
        board.addItem(robot, 5, 4); // Robot at (5,4)
        board.addItem(robot2, 5, 5);
        board.moveItem(robot, -1);// Should push other and move self
        assertEquals(robot2.getY(),6); //See that it's pushed
        
        robot.turn(LeftRight.LEFT, 2); // Turns other way
        board.moveItem(robot, -2);// Moves back where it came from
        assertTrue(robot.getX() == 5 && robot.getY() == 3 && robot2.getY() == 6); //Everything's good
    }
    
    @Test
    @DisplayName("Testting that moving of the board kills the robot")
    /**
     * Magnus is an idiot and moves his robot of the board and kills it
     */
    public void moveRobotOfTheBoardTest() {
        Robot robot = new Robot("Main", Direction.NORTH); // Faces SOUTH
        Board board = new Board(12, 12);
        board.addItem(robot, 11, 11); // Robot at (5,4)
        board.moveItem(robot, 1);// Should fall of
        
        assertFalse(board.getObjects().contains(robot)); //Everything's good
    }

}
